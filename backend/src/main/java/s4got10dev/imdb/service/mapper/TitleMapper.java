package s4got10dev.imdb.service.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;
import s4got10dev.imdb.domain.GenreEntity;
import s4got10dev.imdb.domain.TitleEntity;
import s4got10dev.imdb.api.dto.TitleRatingDTO;
import org.mapstruct.Mapper;
import s4got10dev.imdb.api.dto.TitleShortDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Mapper(componentModel = "spring")
public interface TitleMapper {

    @Mapping(source = "genres", target = "genres", qualifiedByName = "genreNames")
    TitleShortDTO mapToShort(TitleEntity title);

    List<TitleShortDTO> mapToShort(List<TitleEntity> titles);

    @Named("genreNames")
    default String genreNames(List<GenreEntity> genres) {
        return CollectionUtils.isEmpty(genres) ? "" : genres.stream().map(GenreEntity::getName).collect(Collectors.joining(","));
    }

    TitleRatingDTO mapToRating(TitleEntity title);

    List<TitleRatingDTO> mapToRating(List<TitleEntity> titles);

}
