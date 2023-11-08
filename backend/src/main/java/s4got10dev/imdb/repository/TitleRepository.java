package s4got10dev.imdb.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import s4got10dev.imdb.domain.TitleEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface TitleRepository extends Neo4jRepository<TitleEntity, Long>  {

    @Query("MATCH (t:Title)-[r:HAS_GENRE]->(g:Genre) " +
            "WHERE toLower(g.name) = toLower($genre) AND t.averageRating > 0 " +
            "RETURN t ORDER BY t.averageRating DESC, t.numVotes DESC LIMIT $limit")
    List<TitleEntity> findTopRatedByGenre(@Param("genre") String genre, @Param("limit") int limit);

    @Query("MATCH (t:Title)-[r:HAS_GENRE]->(g:Genre) WHERE t.originalTitle = $name OR t.primaryTitle = $name RETURN t,r,g")
    List<TitleEntity> findByTitle(@Param("name") String name);

    @Query("WITH $names AS names MATCH (p:Person) WHERE p.name in names " +
            "WITH collect(p) as persons WITH head(persons) as head, tail(persons) as persons " +
            "MATCH (head)-[:ACTED_IN]->(t:Title) WHERE ALL(p in persons WHERE (p)-[:ACTED_IN]->(t)) RETURN t")
    List<TitleEntity> findByActors(@Param("names") String... names);

    @Query("MATCH (t:Title)<-[r:ACTED_IN]-(a:Person) RETURN t,r,a LIMIT $limit")
    Collection<TitleEntity> graph(@Param("limit") int limit);
}
