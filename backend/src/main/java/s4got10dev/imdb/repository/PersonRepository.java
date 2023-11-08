package s4got10dev.imdb.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import s4got10dev.imdb.domain.PersonEntity;
import s4got10dev.imdb.api.dto.GenresCountDTO;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {

    @Query("MATCH (t:Title)<-[r:ACTED_IN]-(p:Person) WHERE p.name = $name RETURN count(t)")
    Integer countTitlesForActorName(@Param("name") String name);

    @Query("MATCH (g:Genre)<-[gr:HAS_GENRE]-(t:Title)<-[r:ACTED_IN]-(p:Person) " +
            "WHERE p.name = $name RETURN g AS genre, count(gr) AS count")
    List<GenresCountDTO> getGenresCountForActorName(@Param("name") String name);

    @Query("MATCH (p1:Person { name: $name1}),(p2:Person { name: $name2 }), p = shortestPath((p1)-[:ACTED_IN*]-(p2)) RETURN min(length(p))")
    int getDegreeOfSeparation(@Param("name1") String nameOfFirstPerson, @Param("name2") String nameOfSecondPerson);

}
