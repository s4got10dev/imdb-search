package s4got10dev.imdb.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Builder
@Data
@RelationshipProperties
public class Participant {

    @Id
    @GeneratedValue
    private Long id;
    private String category;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @TargetNode
    private PersonEntity person;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @TargetNode
    private TitleEntity title;
}
