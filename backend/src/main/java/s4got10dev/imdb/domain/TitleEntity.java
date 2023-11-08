package s4got10dev.imdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Data
@Node("Title")
public class TitleEntity {

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
    private List<GenreEntity> genres;

    @JsonIgnoreProperties("title")
    @Relationship(type = "ACTED_IN", direction = INCOMING)
    private List<Role> roles;

    @JsonIgnoreProperties("title")
    @Relationship(type = "WORKED_IN", direction = INCOMING)
    private List<Participant> workers;
}
