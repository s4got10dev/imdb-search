package s4got10dev.imdb.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@NodeEntity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String personId;
    private String name;

    @Relationship(type = "ACTED_IN")
    private List<Title> titles = new ArrayList<>();

}
