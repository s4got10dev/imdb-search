package s4got10dev.imdb.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import s4got10dev.imdb.domain.Genre;
import s4got10dev.imdb.domain.Person;
import s4got10dev.imdb.domain.Role;
import s4got10dev.imdb.domain.Title;
import s4got10dev.imdb.service.dto.GenresCountDTO;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.tuple.Pair.of;
import static org.assertj.core.api.Assertions.assertThat;
import static s4got10dev.imdb.TestDataFactory.*;

@RunWith(SpringRunner.class)
@DataNeo4jTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class RepositoryIntTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TitleRepository titleRepository;

    @Before
    public void setUp() {
        personRepository.deleteAll();
        titleRepository.deleteAll();

        Person kb = person("nm0000102", "Kevin Bacon");
        Person ks = person("nm4936479", "Keith Simanton");
        Person hw = person("nm0915989", "Hugo Weaving");

        List<Genre> gs = singletonList(genre("Short"));
        List<Genre> gt = singletonList(genre("Talk-Show"));

        List<String> sr = singletonList("Self");

        Title un = title("tt4798778", "Unexpected", 9.9, 50L, gs);
        Role ksun = role(un, ks, sr);
        un.setRoles(singletonList(ksun));

        Title ia = title("tt5070048", "IMDb Asks", 10.0, 1L, gt);
        Role ksia = role(ia, ks, sr);
        ia.setRoles(singletonList(ksia));

        Title cc = title("tt4800966", "Cop Car", 9.7, 100L, gs);
        Role kbcc = role(cc, kb, sr);
        Role kscc = role(cc, ks, sr);
        cc.setRoles(Arrays.asList(kbcc, kscc));

        Title sl = title("tt4800960", "Strangerland", 9.7, 150L, gs);
        Role hwsl = role(sl, hw, sr);
        Role kssl = role(sl, ks, sr);
        sl.setRoles(Arrays.asList(hwsl, kssl));

        personRepository.save(kb);
        personRepository.save(ks);
        personRepository.save(hw);

        titleRepository.save(un);
        titleRepository.save(ia);
        titleRepository.save(cc);
        titleRepository.save(sl);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void topRatingTitlesFoundByGenre() {
        List<Title> titles = titleRepository.findTopRatedByGenre("Short", 10);
        assertThat(titles).isNotNull().hasSize(3)
                .isSortedAccordingTo(comparing(Title::getAverageRating).thenComparing(Title::getNumVotes).reversed())
                .extracting(Title::getPrimaryTitle).containsExactly("Unexpected", "Strangerland", "Cop Car");
    }


    @Test
    public void titleFoundByOriginalOrPrimaryTitle() {
        String originalTitle = "Strangerland";
        List<Title> titles = titleRepository.findByTitle(originalTitle);
        assertThat(titles).isNotNull().hasSize(1).extracting(Title::getOriginalTitle).contains(originalTitle);
        String primaryTitle = "Unexpected";
        titles = titleRepository.findByTitle(primaryTitle);
        assertThat(titles).isNotNull().hasSize(1).extracting(Title::getPrimaryTitle).contains(primaryTitle);
    }

    @Test
    public void titleFoundByActors() {
        List<Title> titles = titleRepository.findByActors("Kevin Bacon", "Keith Simanton");
        assertThat(titles).isNotNull().hasSize(1).extracting(Title::getOriginalTitle).contains("Cop Car");
    }

    @Test
    public void titlesCountedForProvidedActorName() {
        assertThat(personRepository.countTitlesForActorName("Keith Simanton")).isEqualTo(4);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void genresActedInCountedForProvidedActorName() {
        List<GenresCountDTO> genresCount = personRepository.getGenresCountForActorName("Keith Simanton");
        assertThat(genresCount).isNotNull().hasSize(2)
                .extracting(gc -> of(gc.getGenre().getName(), gc.getCount()))
                .containsExactlyInAnyOrder(of("Short", 3), of("Talk-Show", 1));
    }

    @Test
    public void degreeOfSeparationBetweenPersonsCounted() {
        assertThat(personRepository.getDegreeOfSeparation("Hugo Weaving", "Kevin Bacon")).isEqualTo(4);
    }

}
