package s4got10dev.imdb.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import s4got10dev.imdb.service.SearchService;
import s4got10dev.imdb.api.dto.ActorTypecastDTO;
import s4got10dev.imdb.api.dto.TitleRatingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s4got10dev.imdb.api.dto.TitleShortDTO;

import java.util.*;

@Tag(name = "IMDb Search")
@RequiredArgsConstructor
@RestController
@RequestMapping("/imdb")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Returns list of top rated titles for specified genre")
    @GetMapping("/top-rated-for-genre")
    public ResponseEntity<List<TitleRatingDTO>> getTopRatedByGenre(@RequestParam(value = "genre") String genre,
                                                                   @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return ResponseEntity.ok(searchService.findTopRatedByGenre(genre, limit));
    }

    @Operation(summary = "Determine if that person is typecast (at least half of their work is one genre)")
    @GetMapping("/is-actor-typecasted")
    public ResponseEntity<ActorTypecastDTO> isActorTypeCasted(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(searchService.isActorTypeCasted(name));
    }

    @Operation(summary = "Returns title which have specified 'Original Title' or 'Primary Title'")
    @GetMapping("/find-title")
    public ResponseEntity<List<TitleShortDTO>> findByTitle(@RequestParam(value = "title") String title) {
        return ResponseEntity.ok(searchService.findByTitle(title));
    }

    @Operation(summary = "List of movies or TV shows that both people have shared")
    @GetMapping("/find-coincidence")
    public ResponseEntity<List<TitleShortDTO>> findByActors(@RequestParam(value = "names") String... names) {
        return ResponseEntity.ok(searchService.findByActors(names));
    }

    @Operation(summary = "Determine degree of separation between the specified person and Kevin Bacon")
    @GetMapping("/find-degree-of-separation")
    public ResponseEntity<Integer> findDegreeOfSeparation(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(searchService.findDegreeOfSeparation("Kevin Bacon", name));
    }

}
