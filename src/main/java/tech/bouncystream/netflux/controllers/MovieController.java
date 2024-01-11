package tech.bouncystream.netflux.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.bouncystream.netflux.domain.Movie;
import tech.bouncystream.netflux.domain.MovieEvent;
import tech.bouncystream.netflux.services.MovieService;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("{id}")
    Mono<Movie> movie(@PathVariable final String id) {
        return movieService.movieById(id);
    }


    @GetMapping()
    Flux<Movie> movies() {
        return movieService.allMovies();
    }

    @GetMapping(value="/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<MovieEvent> streamMovieEvents(@PathVariable final String id) {
        return movieService.streamMovieEvents(id);
    }

}
