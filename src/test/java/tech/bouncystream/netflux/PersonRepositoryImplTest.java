package tech.bouncystream.netflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.bouncystream.netflux.domain.Person;
import tech.bouncystream.netflux.repositories.PersonRepository;
import tech.bouncystream.netflux.repositories.PersonRepositoryImpl;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersonRepositoryImplTest {

    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new PersonRepositoryImpl();
    }

    @Test
    void getByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);
        Person person = personMono.block();
        System.out.println(person.toString());
    }

    @Test
    void getByIdSubscribe() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.subscribe(person -> System.out.println(person));
    }

    @Test
    void getByIdMapFunction() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.map(person -> {
            System.out.println(person);
            return person.firstName();
        }).subscribe(firstName -> System.out.println("from map: " + firstName));
    }

    @Test
    void fluxTestBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();

        final var firstPerson = personFlux.blockFirst();
        System.out.println(firstPerson);

    }

    @Test
    void fluxTestSubscribe() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(person -> System.out.println(person));
    }

    @Test
    void fluxToListMono() {
        Flux<Person> personFlux = personRepository.findAll();
        Mono<List<Person>> personListMono = personFlux.collectList();

        personListMono.subscribe(list -> {
            list.forEach(person -> {
                System.out.println(person);
            });
        });
    }

    @Test
    void testFindPersonById() {
        Flux<Person> personFlux = personRepository.findAll();
        final var id = 3;
        Mono<Person> personMono = personFlux.filter(person -> person.id() == id).last();

        personMono.subscribe(p -> System.out.println(p));
    }

    @Test
    void testFindPersonByIdNotFound() {
        Flux<Person> personFlux = personRepository.findAll();
        final var id = 8;

        // with next(), no exception is thrown, Mono subscribe doesn't get anything
        Mono<Person> personMono = personFlux.filter(person -> person.id() == id).next();

        personMono.subscribe(p -> System.out.println(p));
    }

    @Test
    void testFindPersonByIdNotFoundWithException() {
        Flux<Person> personFlux = personRepository.findAll();
        final var id = 5;
        Mono<Person> personMono = personFlux.filter(person -> person.id() == id).single();

        personMono.doOnError(throwable -> System.out.println("Oops."))
                .onErrorReturn(new Person(78, null, null))
                .subscribe(p -> System.out.println(p));
    }

    @Test
    void testShouldFindPersonById() {
        final var personMono = personRepository.getById(2);
        final AtomicReference<Person> personFound = new AtomicReference<>();
        personMono.doOnError(throwable -> System.err.println("Oops!"))
                .doOnSuccess(p -> personFound.set(p)).subscribe();

        assert (personFound.get().firstName().equals("Fiona"));
    }

    @Test
    void testShouldLookForPersonByIdAndNotFindIt() {
        final var personMono = personRepository.getById(8);
        final AtomicReference<Person> personFound = new AtomicReference<>();
        // next() does not throw an error when nothing is found
        personMono.doOnError(throwable -> System.out.println("Oops!"))
                .doOnSuccess(p -> {
                    System.out.println("Person found:" + p);
                    personFound.set(p);
                }).subscribe();

        assert (personFound.get() == null);
    }

    @Test
    void testShouldLookForPersonAndVerifyWithStepVerifier() {
        final var personMono = personRepository.getById(2);
        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();
        StepVerifier.create(personMono).expectNext(new Person(2, "Fiona", "Glenanne")).verifyComplete();

    }


}
