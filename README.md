# github-retriever

A server application that returns data from GitHub about users' repositories and number of their total stargazers. Written during an Allegro Summer e-Xperience 2021.

## Getting started

### Setup
Building this projects requires Java 11 installed. 

### Running

From project root run the command:


```
$ ./gradlew bootRun
```

### Testing

To run unit and integration tests run the command:


```
$ ./gradlew test
```

### API

After booting the project your application should run on your localhost on 8080 port. Now you can call 2 endpoints:

`GET /users/:user/repos` - endpoint returns list of user's repositories in json format.

`GET /users/:user/stars` - endpoint returns sum of all user's repositories stargazers in json format.


### Project assumptions

1. Project was written in clean, OOP way and layered architecture, that investment makes it open for future modifications. 

1. For unauthenticated requests the GitHub api rate limit allows for up to 60 requests per hour. To limit problem with number of requests restrictions I decided to add a cache on http client layer with time to live of 5 minutes.

1. My application returns http status 'not found' for user that doesn't exist on GitHub, another errors causes 'internal server error'.

1. I assumpted that common GitHub user doesn't have more than 100 repositories, thus I haven't implemented page mechanism.

1. I wrote unit and integration tests, that are called from same command for sake of simplicity.

1. I created interface of a GitHubClient with REST implementation so it's open for future extenstion, for example GraphQL implementation.


### Future development suggestions

1. Possibility of saving GitHub authentication token in properties file to increase api's rate limit. 

1. Adding the way of adhoc cache eviction. 

1. Implementing paging mechanism.

1. User-friendly front-end ui.

1. Other methods based on our client's needs.


