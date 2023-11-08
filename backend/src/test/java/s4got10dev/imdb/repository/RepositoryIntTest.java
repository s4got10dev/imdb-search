package s4got10dev.imdb.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Neo4jContainer;
import s4got10dev.imdb.domain.GenreEntity;
import s4got10dev.imdb.domain.PersonEntity;
import s4got10dev.imdb.domain.Role;
import s4got10dev.imdb.domain.TitleEntity;
import s4got10dev.imdb.api.dto.GenresCountDTO;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.tuple.Pair.of;
import static org.assertj.core.api.Assertions.assertThat;
import static s4got10dev.imdb.TestDataFactory.*;

@DataNeo4jTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class RepositoryIntTest {

    private static Neo4jContainer<?> neo4jContainer;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    TitleRepository titleRepository;


    @BeforeAll
    static void initializeNeo4j() {
        neo4jContainer = new Neo4jContainer<>("neo4j").withAdminPassword("somePassword");
        neo4jContainer.start();
    }

    @AfterAll
    static void stopNeo4j() {
        neo4jContainer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();
        titleRepository.deleteAll();

        PersonEntity kb = person("nm0000102", "Kevin Bacon");
        PersonEntity ks = person("nm4936479", "Keith Simanton");
        PersonEntity hw = person("nm0915989", "Hugo Weaving");

        List<GenreEntity> gs = singletonList(genre("Short"));
        List<GenreEntity> gt = singletonList(genre("Talk-Show"));

        List<String> sr = singletonList("Self");

        TitleEntity un = title("tt4798778", "Unexpected", 9.9, 50L, gs);
        Role ksun = role(un, ks, sr);
        un.setRoles(singletonList(ksun));

        TitleEntity ia = title("tt5070048", "IMDb Asks", 10.0, 1L, gt);
        Role ksia = role(ia, ks, sr);
        ia.setRoles(singletonList(ksia));

        TitleEntity cc = title("tt4800966", "Cop Car", 9.7, 100L, gs);
        Role kbcc = role(cc, kb, sr);
        Role kscc = role(cc, ks, sr);
        cc.setRoles(Arrays.asList(kbcc, kscc));

        TitleEntity sl = title("tt4800960", "Strangerland", 9.7, 150L, gs);
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
    void topRatingTitlesFoundByGenre() {
        List<TitleEntity> titles = titleRepository.findTopRatedByGenre("Short", 10);
        assertThat(titles).isNotNull().hasSize(3)
                .isSortedAccordingTo(comparing(TitleEntity::getAverageRating).thenComparing(TitleEntity::getNumVotes).reversed())
                .extracting(TitleEntity::getPrimaryTitle).containsExactly("Unexpected", "Strangerland", "Cop Car");
    }


    @Test
    void titleFoundByOriginalOrPrimaryTitle() {
        String originalTitle = "Strangerland";
        List<TitleEntity> titles = titleRepository.findByTitle(originalTitle);
        assertThat(titles).isNotNull().hasSize(1).extracting(TitleEntity::getOriginalTitle).contains(originalTitle);
        String primaryTitle = "Unexpected";
        titles = titleRepository.findByTitle(primaryTitle);
        assertThat(titles).isNotNull().hasSize(1).extracting(TitleEntity::getPrimaryTitle).contains(primaryTitle);
    }

    @Test
    void titleFoundByActors() {
        List<TitleEntity> titles = titleRepository.findByActors("Kevin Bacon", "Keith Simanton");
        assertThat(titles).isNotNull().hasSize(1).extracting(TitleEntity::getOriginalTitle).contains("Cop Car");
    }

    @Test
    void titlesCountedForProvidedActorName() {
        assertThat(personRepository.countTitlesForActorName("Keith Simanton")).isEqualTo(4);
    }

    @SuppressWarnings("unchecked")
    @Test
    void genresActedInCountedForProvidedActorName() {
        List<GenresCountDTO> genresCount = personRepository.getGenresCountForActorName("Keith Simanton");
        assertThat(genresCount).isNotNull().hasSize(2)
                .extracting(gc -> of(gc.genre().getName(), gc.count()))
                .containsExactlyInAnyOrder(of("Short", 3), of("Talk-Show", 1));
    }

    @Test
    void degreeOfSeparationBetweenPersonsCounted() {
        assertThat(personRepository.getDegreeOfSeparation("Hugo Weaving", "Kevin Bacon")).isEqualTo(4);
    }


}
