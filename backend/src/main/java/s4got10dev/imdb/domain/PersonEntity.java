package s4got10dev.imdb.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@Node("Person")
public class PersonEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String personId;
    private String name;

    @Relationship(type = "ACTED_IN")
    private List<TitleEntity> titles = new ArrayList<>();

}
