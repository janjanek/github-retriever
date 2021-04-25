package com.allegro.retriever;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.NestedServletException;

import java.net.SocketTimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GithubDataRetrieverIntegrationTests {

    @Value("${wiremock_port}")
    private int wiremockPort;

    @Autowired
    private MockMvc mockMvc;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        this.wireMockServer = new WireMockServer(wiremockPort);
        this.wireMockServer.start();
        this.wireMockServer.resetAll();
    }

    @AfterEach
    public void after() {
        this.wireMockServer.stop();
    }

    @Test
    public void should_return_stars_count_from_endpoint() throws Exception {
        //given
        returns200onGet("allegro", reposBody());

        //when
        ResultActions result = this.mockMvc.perform(get("/users/allegro/stars"));

        //then
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.starsCount").value(8));
    }

    @Test
    public void should_return_repos_from_endpoint() throws Exception {
        //given
        returns200onGet("ebay", reposBody());

        //when
        ResultActions result = this.mockMvc.perform(get("/users/ebay/repos"));

        //then
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.repos", hasSize(2)))
                .andExpect(jsonPath("$.repos[0].name").value("repo1"))
                .andExpect(jsonPath("$.repos[1].name").value("repo2"))
                .andExpect(jsonPath("$.repos[0].stars").value("2"))
                .andExpect(jsonPath("$.repos[1].stars").value("6"));
    }


    @Test
    public void should_return_404_when_no_user_exists() throws Exception {
        //given
        returns404onGet("google");

        //when
        ResultActions result = this.mockMvc.perform(get("/users/google/repos"));

        //then
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void should_return_500_on_timeout() {
        //given
        returns200onGetAfterSeconds("amazon", reposBody(), 5);

        //expect
        NestedServletException exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(get("/users/amazon/repos"));
        });
        assertEquals(exception.getCause().getClass(), ResourceAccessException.class);
        assertEquals(exception.getCause().getCause().getClass(), SocketTimeoutException.class);
    }

    private String reposBody() {
        return "[{ \"name\": \"repo1\", \"stargazers_count\": 2},{ \"name\": \"repo2\", \"stargazers_count\": 6}]";
    }

    private void returns200onGet(String user, String body) {
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo(path(user)))
                .willReturn(aResponse().withBody(body)
                        .withHeader("Content-Type", "application/json")));
    }

    private void returns200onGetAfterSeconds(String user, String body, int seconds) {
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo(path(user)))
                .willReturn(aResponse().withBody(body)
                        .withFixedDelay(seconds * 1000)
                        .withHeader("Content-Type", "application/json"))
        );
    }

    private void returns404onGet(String user) {
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo(path(user)))
                .willReturn(aResponse().withStatus(404)));
    }

    private String path(String user) {
        return "/users/" + user + "/repos?page=1&per_page=100";
    }

}
