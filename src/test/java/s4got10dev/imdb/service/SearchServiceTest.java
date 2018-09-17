package s4got10dev.imdb.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import s4got10dev.imdb.repository.PersonRepository;
import s4got10dev.imdb.repository.TitleRepository;
import s4got10dev.imdb.service.dto.ActorTypecastDTO;
import s4got10dev.imdb.service.impl.SearchServiceImpl;
import s4got10dev.imdb.service.mapper.TitleMapper;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static s4got10dev.imdb.TestDataFactory.genreCount;

@RunWith(SpringRunner.class)
public class SearchServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private TitleRepository titleRepository;
    @MockBean
    private TitleMapper mapper;

    private SearchService searchService;

    private final String kr = "Keanu Reaves";
    private final String jc = "Jim Carrey";

    @Before
    public void setUp() {
        searchService = new SearchServiceImpl(titleRepository, personRepository, mapper);
        when(personRepository.countTitlesForActorName(kr)).thenReturn(37);
        when(personRepository.getGenresCountForActorName(kr))
                .thenReturn(asList(genreCount("Horror", 6), genreCount("Adventure", 11),
                        genreCount("Action", 15), genreCount("Family", 4)));

        when(personRepository.countTitlesForActorName(jc)).thenReturn(42);
        when(personRepository.getGenresCountForActorName(jc))
                .thenReturn(asList(genreCount("Comedy", 34), genreCount("Adventure", 7),
                        genreCount("Family", 10), genreCount("Drama", 4)));
    }

    @Test
    public void actorDefinedAsNotTypecast() {
        assertThat(searchService.isActorTypeCasted(kr))
                .matches(r -> !r.isTypecast())
                .hasFieldOrPropertyWithValue("message", "Actor " + kr + " is not typecast");
    }

    @Test
    public void actorDefinedAsTypecast() {
        assertThat(searchService.isActorTypeCasted(jc))
                .matches(ActorTypecastDTO::isTypecast)
                .hasFieldOrPropertyWithValue("message", "Actor " + jc + " is typecast in Comedy genre");
    }
}
