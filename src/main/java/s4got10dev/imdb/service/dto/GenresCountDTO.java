package s4got10dev.imdb.service.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;
import s4got10dev.imdb.domain.Genre;

@Data
@QueryResult
public class GenresCountDTO {
    private Genre genre;
    private int count;
}