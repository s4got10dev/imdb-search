package s4got10dev.imdb.repository;

import s4got10dev.imdb.domain.Title;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TitleRepository extends Neo4jRepository<Title, Long>  {

    @Query("MATCH (t:Title)-[r:HAS_GENRE]->(g:Genre) " +
            "WHERE LOWER(g.name) = LOWER({genre}) AND t.averageRating > 0 " +
            "RETURN t ORDER BY t.averageRating DESC, t.numVotes DESC LIMIT {limit}")
    List<Title> findTopRatedByGenre(@Param("genre") String genre, @Param("limit") int limit);

    @Query("MATCH (t:Title)-[r:HAS_GENRE]->(g:Genre) WHERE t.originalTitle = {name} OR t.primaryTitle = {name} RETURN t,r,g")
    List<Title> findByTitle(@Param("name") String name);

    @Query("WITH {names} AS names MATCH (p:Person) WHERE p.name in names " +
            "WITH collect(p) as persons WITH head(persons) as head, tail(persons) as persons " +
            "MATCH (head)-[:ACTED_IN]->(t:Title) WHERE ALL(p in persons WHERE (p)-[:ACTED_IN]->(t)) RETURN t")
    List<Title> findByActors(@Param("names") String... names);

    @Query("MATCH (t:Title)<-[r:ACTED_IN]-(a:Person) RETURN t,r,a LIMIT {limit}")
    Collection<Title> graph(@Param("limit") int limit);
}
