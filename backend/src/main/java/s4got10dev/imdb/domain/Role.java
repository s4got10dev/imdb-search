package s4got10dev.imdb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RelationshipProperties
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private List<String> roles = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @TargetNode
    private PersonEntity person;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @TargetNode
    private TitleEntity title;
}
