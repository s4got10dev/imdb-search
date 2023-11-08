package s4got10dev.imdb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s4got10dev.imdb.api.dto.ActorTypecastDTO;
import s4got10dev.imdb.api.dto.GenresCountDTO;
import s4got10dev.imdb.api.dto.TitleRatingDTO;
import s4got10dev.imdb.api.dto.TitleShortDTO;
import s4got10dev.imdb.repository.PersonRepository;
import s4got10dev.imdb.repository.TitleRepository;
import s4got10dev.imdb.service.SearchService;
import s4got10dev.imdb.service.mapper.TitleMapper;

import java.util.Comparator;
import java.util.List;

import static s4got10dev.imdb.api.dto.ActorTypecastDTO.actorIsNotTypecasted;
import static s4got10dev.imdb.api.dto.ActorTypecastDTO.actorIsTypecasted;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final TitleRepository titleRepository;
    private final PersonRepository personRepository;
    private final TitleMapper mapper;

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
        return genres.stream().max(Comparator.comparing(GenresCountDTO::count))
                .filter(gc -> gc.count() * 2 >= playedIn)
                .map(gc -> actorIsTypecasted(name, gc.genre().getName()))
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
