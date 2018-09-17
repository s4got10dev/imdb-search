# IMDb Search
Application provide some search possibilities for [IMDb datasets](https://www.imdb.com/interfaces/)

## How to use
Project already has gradle wrapper (so you don't need to have it installed). 
All other dependencies will be downloaded by it.

Web UI page using [OMDAPI](http://www.omdbapi.com) for accessing details of Titles,
so, please, make sure you have your own key [generated](http://www.omdbapi.com/apikey.aspx) and put it inside
[imdb-search.js line 31](static/assets/js/imdb-search.js) as apikey parameter
 
###Database
Project don't have embedded Neo4j configured for dev purposes yet so Docker or installed Neo4j instance required
in order to work with project.

*take a note that IMDB_DATA system variable should be set to folder where dataset files exists or should be downloaded*
* [src/main/docker](src/main/docker) contains docker compose files which maps IMDB_DATA directory
* [src/main/resources/cql](src/main/resources/cql) contains Cypher scripts that should be used for data import
*from my experience scripts run faster if you are using [Neo4j browser](http://localhost:7474/browser/)*
####In case of test database
* [src/main/resources/test-datasets](src/main/resources/test-datasets)  contains very small dataset for data import
####In case of real database 
* [prepare_datasets.sh](src/main/resources/bash/prepare_datasets.sh) could help with downloading IMDb datasets 
and preparing them for import

###Execution
Execute gradle build (It may take a while in the first run):
```sh
$ ./gradlew build
```
After the message of BUILD SUCCESS, the application is ready to run:
```sh
$ ./gradlew bootRun
```

Application will be available on http://localhost:8787 (This one can be also changed in `application.properties`).
REST API documentation will be available at http://localhost:8787/swagger-ui.html
[postman collection](src/test/resources/IMDb.postman_collection.json) could be used for REST API calls

## Tech stack
- Backend
    - Java 8
    - Spring Boot
        - Web
	    - Data Neo4j
	    - Test
    - Lombok
    - Mapstruct
    - SpringFox Swagger UI
- Storage
	- Neo4j
	- Embedded Neo4j for tests
- Frontend
    - JQuery
    - Bootstrap 4
    - Paper Dashboard template - see [official page](https://www.creative-tim.com/product/paper-dashboard)
- Gradle
- Docker compose

