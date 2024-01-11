package tech.bouncystream.netflux.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.bouncystream.netflux.domain.Person;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {


    private Person michael = new Person(1, "Michael", "Weston");
    private Person fiona = new Person(2, "Fiona", "Glenanne");
    private Person sam = new Person(3, "Sam", "Axe");
    private Person jesse = new Person(4, "Jesse", "Porter");

    @Override
    public Mono<Person> getById(Integer id) {
        final var personsFlux = Flux.just(michael, fiona, sam, jesse);
        return personsFlux.filter(p -> p.id() == id).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, fiona, sam, jesse);
    }
}
