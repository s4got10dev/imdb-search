package s4got10dev.imdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("s4got10dev.imdb.domain")
@EnableNeo4jRepositories("s4got10dev.imdb.repository")
public class Neo4jConfig {
}
