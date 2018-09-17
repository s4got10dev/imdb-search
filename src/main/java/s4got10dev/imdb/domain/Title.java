package s4got10dev.imdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@Data
@NodeEntity
public class Title {

    @Id
    @GeneratedValue
    private Long id;
    private String titleId;
    private String primaryTitle;
    private String originalTitle;
    private Double averageRating;
    private Long numVotes;

    @JsonIgnoreProperties("title")
    @Relationship(type = "HAS_GENRE")
    private List<Genre> genres;

    @JsonIgnoreProperties("title")
    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<Role> roles;

    @JsonIgnoreProperties("title")
    @Relationship(type = "WORKED_IN", direction = Relationship.INCOMING)
    private List<Participant> workers;
}
