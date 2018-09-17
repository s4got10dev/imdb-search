package s4got10dev.imdb.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

@Builder
@Data
@RelationshipEntity(type = "WORKED_IN")
public class Participant {

    @Id
    @GeneratedValue
    private Long id;
    private String category;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @StartNode
    private Person person;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @EndNode
    private Title title;
}
