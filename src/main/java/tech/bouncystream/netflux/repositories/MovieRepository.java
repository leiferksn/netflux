package tech.bouncystream.netflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tech.bouncystream.netflux.domain.Movie;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {



}
