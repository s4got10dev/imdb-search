package s4got10dev.imdb.service.impl;

import s4got10dev.imdb.repository.PersonRepository;
import s4got10dev.imdb.repository.TitleRepository;
import s4got10dev.imdb.service.dto.GenresCountDTO;
import s4got10dev.imdb.service.SearchService;
import s4got10dev.imdb.service.dto.ActorTypecastDTO;
import s4got10dev.imdb.service.dto.TitleShortDTO;
import s4got10dev.imdb.service.dto.TitleRatingDTO;
import s4got10dev.imdb.service.mapper.TitleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static s4got10dev.imdb.service.dto.ActorTypecastDTO.actorIsNotTypecasted;
import static s4got10dev.imdb.service.dto.ActorTypecastDTO.actorIsTypecasted;
import static java.util.Comparator.comparing;

@Service
public class SearchServiceImpl implements SearchService {

    private TitleRepository titleRepository;
    private PersonRepository personRepository;
    private TitleMapper mapper;

    public SearchServiceImpl(TitleRepository titleRepository, PersonRepository personRepository, TitleMapper mapper) {
        this.titleRepository = titleRepository;
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TitleRatingDTO> findTopRatedByGenre(String genre, int limit) {
        return mapper.mapToRating(titleRepository.findTopRatedByGenre(genre, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public ActorTypecastDTO isActorTypeCasted(String name) {
        int playedIn = personRepository.countTitlesForActorName(name);
        List<GenresCountDTO> genres = personRepository.getGenresCountForActorName(name);
        return genres.stream().max(comparing(GenresCountDTO::getCount))
                .filter(gc -> gc.getCount() * 2 >= playedIn)
                .map(gc -> actorIsTypecasted(name, gc.getGenre().getName()))
                .orElseGet(() -> actorIsNotTypecasted(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TitleShortDTO> findByTitle(String title) {
        return mapper.mapToShort(titleRepository.findByTitle(title));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TitleShortDTO> findByActors(String... names) {
        return mapper.mapToShort(titleRepository.findByActors(names));
    }

    @Override
    @Transactional(readOnly = true)
    public int findDegreeOfSeparation(String name1, String name2) {
        return personRepository.getDegreeOfSeparation(name1, name2);
    }

}
