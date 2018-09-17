package s4got10dev.imdb.service.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import s4got10dev.imdb.domain.Genre;
import s4got10dev.imdb.domain.Title;
import s4got10dev.imdb.service.dto.TitleRatingDTO;
import org.mapstruct.Mapper;
import s4got10dev.imdb.service.dto.TitleShortDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Mapper(componentModel = "spring")
public interface TitleMapper {

    @Mapping(source = "genres", target = "genres", qualifiedByName = "genreNames")
    TitleShortDTO mapToShort(Title title);

    List<TitleShortDTO> mapToShort(List<Title> titles);

    @Named("genreNames")
    default String genreNames(List<Genre> genres) {
        return isEmpty(genres) ? "" : genres.stream().map(Genre::getName).collect(Collectors.joining(","));
    }

    TitleRatingDTO mapToRating(Title title);

    List<TitleRatingDTO> mapToRating(List<Title> titles);

}
