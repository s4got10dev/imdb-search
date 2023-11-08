# IMDb Search

## 🔎 About
Application provides some search possibilities for [IMDb datasets](https://www.imdb.com/interfaces/)

## 📖 How to use
The Project already has gradle wrapper (so you don't need to have it installed). 
It will download all other dependencies.

Web UI page using [OMDb API](http://www.omdbapi.com) for accessing details of Titles,
so, please, make sure you have your own key [generated](http://www.omdbapi.com/apikey.aspx) and put it inside
[imdb-search.js line 31](backend/src/main/resources/static/assets/js/imdb-search.js) as apikey parameter
 
## 💾 Database
Project doesn't have embedded Neo4j configured for dev purposes, so Docker or installed Neo4j instance is required
 to work with a project.
See docker compose files in [docker](docker) folder.

### 📁 Data import
*take a note that IMDB_DATA system variable should be set to folder where dataset files exists or should be downloaded*
* [docker](docker) contains docker compose files which maps IMDB_DATA directory
* [cql](backend/src/main/resources/cql) contains Cypher scripts that should be used for data import

*from my experience scripts run faster if you are using [Neo4j browser](http://localhost:7474/browser/)*

#### In the case of test database
* [test-datasets](backend/src/main/resources/test-datasets) contains very small dataset for data import
#### In the case of real database 
* [prepare_datasets.sh](backend/src/main/resources/bash/prepare_datasets.sh) could help with downloading IMDb datasets 
and preparing them for import

## 🚀 Execution
Execute gradle build (It may take a while in the first run):
```sh
./gradlew build
```
After the message of BUILD SUCCESS, the application is ready to run:
```sh
./gradlew bootRun
```

Application will be available on [http://localhost:8769](http://localhost:8769) 
(Can be changed in [application.yaml](backend/src/main/resources/application.yaml)).

Open API documentation will be available at [http://localhost:8769/api-docs](http://localhost:8769/api-docs)
Swagger UI will be available at [http://localhost:8769/swagger-ui/index.html](http://localhost:8769/swagger-ui/index.html)

[http file](api/IMDb.http) contains some examples of REST API calls that could be used for testing

[postman collection](api/IMDb.postman_collection.json) could be used for REST API calls

## 💡Tech stack
- 🧠 **Backend**
    - ☕️ **Java 17**
    - 🍃 **Spring Boot**
      - Web
	  - Data Neo4j
	  - Test
      - SpringDoc OpenAPI+Swagger
    - ⚙️ **Misc**
      - Lombok
      - Mapstruct
    - 🧪 **Tests**
      - JUnit 5
      - Mockito
      - Testcontainers
- 💾 **Storage**
	- 🔗 Neo4j
- 🖼️ **Frontend**
    - JQuery
    - Bootstrap 4
    - Paper Dashboard template [official page](https://www.creative-tim.com/product/paper-dashboard)
- 🏗 **Gradle**
- 🐳 **Docker compose**

