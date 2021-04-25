package com.allegro.retriever;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@ActiveProfiles("test")
class GithubDataRetrieverIntegrationTests {

    @Value("${wiremock_port}")
    private int wiremockPort;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.wireMockServer = new WireMockServer(wiremockPort);
        this.wireMockServer.start();
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
        returns200onGet("allegro", reposBody());

        //when
        ResultActions result = this.mockMvc.perform(get("/users/allegro/repos"));

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
        returns404onGet("allegro");

        //when
        ResultActions result = this.mockMvc.perform(get("/users/allegro/repos"));

        //then
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String reposBody() {
        return "[{ \"name\": \"repo1\", \"stargazers_count\": 2},{ \"name\": \"repo2\", \"stargazers_count\": 6}]";
    }

    private void returns200onGet(String user, String body) {
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo(path(user)))
                .willReturn(aResponse().withBody(body)
                        .withHeader("Content-Type", "application/json")));
    }

    private void returns404onGet(String user) {
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo(path(user)))
                .willReturn(aResponse().withStatus(404)));
    }

    private String path(String user) {
        return "/users/" + user + "/repos?page=1&per_page=100";
    }

}
