package s4got10dev.imdb.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Data
@Node("Genre")
public class GenreEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "GENRE_OF", direction = INCOMING)
    private List<TitleEntity> titles = new ArrayList<>();

}
