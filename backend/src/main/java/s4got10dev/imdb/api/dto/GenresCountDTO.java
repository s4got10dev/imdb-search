package s4got10dev.imdb.api.dto;

import s4got10dev.imdb.domain.GenreEntity;

public record GenresCountDTO(GenreEntity genre, int count) {
}