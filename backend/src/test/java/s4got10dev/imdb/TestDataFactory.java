package s4got10dev.imdb;

import s4got10dev.imdb.domain.GenreEntity;
import s4got10dev.imdb.domain.PersonEntity;
import s4got10dev.imdb.domain.Role;
import s4got10dev.imdb.domain.TitleEntity;
import s4got10dev.imdb.api.dto.GenresCountDTO;

import java.util.List;

public class TestDataFactory {

    public static TitleEntity title(String titleId, String title, Double averageRating, Long numVotes, List<GenreEntity> genres) {
        TitleEntity t = new TitleEntity();
        t.setTitleId(titleId);
        t.setOriginalTitle(title);
        t.setPrimaryTitle(title);
        t.setAverageRating(averageRating);
        t.setNumVotes(numVotes);
        t.setGenres(genres);
        return t;
    }

    public static Role role(TitleEntity title, PersonEntity person, List<String> roles) {
        Role r = new Role();
        r.setTitle(title);
        r.setPerson(person);
        r.setRoles(roles);
        return r;
    }

    public static PersonEntity person(String personId, String name) {
        PersonEntity p = new PersonEntity();
        p.setPersonId(personId);
        p.setName(name);
        return p;
    }

    public static GenreEntity genre(String name) {
        GenreEntity g = new GenreEntity();
        g.setName(name);
        return g;
    }

    public static GenresCountDTO genreCount(String name, int count) {
        return new GenresCountDTO(genre(name), count);
    }

}
