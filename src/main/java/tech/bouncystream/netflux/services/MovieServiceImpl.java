package tech.bouncystream.netflux.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.bouncystream.netflux.domain.Movie;
import tech.bouncystream.netflux.domain.MovieEvent;
import tech.bouncystream.netflux.repositories.MovieRepository;

import java.time.Duration;
import java.util.Date;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Override
    public Mono<Movie> movieById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Flux<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Flux<MovieEvent> streamMovieEvents(String id) {
        return Flux.<MovieEvent>generate(movieEventSynchronousSink -> {
            movieEventSynchronousSink.next(new MovieEvent(id, new Date()));
        }).delayElements(Duration.ofSeconds(1));
    }
}
