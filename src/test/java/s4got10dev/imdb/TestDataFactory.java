package s4got10dev.imdb;

import s4got10dev.imdb.domain.Genre;
import s4got10dev.imdb.domain.Person;
import s4got10dev.imdb.domain.Role;
import s4got10dev.imdb.domain.Title;
import s4got10dev.imdb.service.dto.GenresCountDTO;

import java.util.List;

public class TestDataFactory {

    public static Title title(String titleId, String title, Double averageRating, Long numVotes, List<Genre> genres) {
        Title t = new Title();
        t.setTitleId(titleId);
        t.setOriginalTitle(title);
        t.setPrimaryTitle(title);
        t.setAverageRating(averageRating);
        t.setNumVotes(numVotes);
        t.setGenres(genres);
        return t;
    }

    public static Role role(Title title, Person person, List<String> roles) {
        Role r = new Role();
        r.setTitle(title);
        r.setPerson(person);
        r.setRoles(roles);
        return r;
    }

    public static Person person(String personId, String name) {
        Person p = new Person();
        p.setPersonId(personId);
        p.setName(name);
        return p;
    }

    public static Genre genre(String name) {
        Genre g = new Genre();
        g.setName(name);
        return g;
    }

    public static GenresCountDTO genreCount(String name, int count) {
        GenresCountDTO gc = new GenresCountDTO();
        gc.setGenre(genre(name));
        gc.setCount(count);
        return gc;
    }

}
