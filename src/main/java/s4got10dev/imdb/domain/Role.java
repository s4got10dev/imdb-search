package s4got10dev.imdb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RelationshipEntity(type = "ACTED_IN")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private List<String> roles = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @StartNode
    private Person person;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @EndNode
    private Title title;
}
