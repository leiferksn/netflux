package tech.bouncystream.netflux.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.bouncystream.netflux.domain.Person;

public interface PersonRepository {

    Mono<Person> getById(Integer id);
    Flux<Person> findAll();

}
