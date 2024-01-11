package tech.bouncystream.netflux.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.bouncystream.netflux.domain.Movie;
import tech.bouncystream.netflux.domain.MovieEvent;

public interface MovieService {

    Mono<Movie> movieById(final String id);

    Flux<Movie> allMovies();

    Flux<MovieEvent> streamMovieEvents(final String id);
}
