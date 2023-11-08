package s4got10dev.imdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
public class ImdbSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbSearchApplication.class, args);
    }

}
