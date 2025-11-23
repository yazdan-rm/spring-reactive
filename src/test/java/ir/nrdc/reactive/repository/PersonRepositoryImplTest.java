package ir.nrdc.reactive.repository;

import ir.nrdc.reactive.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class PersonRepositoryImplTest {

    PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new PersonRepositoryImpl();
    }

    @Test
    void findByIdBlock() {
        Mono<Person> personMono = personRepository.findById(1L);

        Person person = personMono.block();

        System.out.println(person);
    }

    @Test
    void getByIdSubscriber() {
        Mono<Person> personMono = personRepository.findById(1L);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(System.out::println);
    }

    @Test
    void getByIdSubscriberNotFound() {
        Mono<Person> personMono = personRepository.findById(10L);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(System.out::println);
    }

    @Test
    void getByIdMapFunction() {
        Mono<Person> personMono = personRepository.findById(1L);

        personMono.map(Person::getFirstName)
                .subscribe(System.out::println);
    }

    @Test
    void fluxTestBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person);
    }

    @Test
    void testFluxSubscribe() {
        Flux<Person> personFlux = personRepository.findAll();

        StepVerifier.create(personFlux).expectNextCount(4).verifyComplete();

        personFlux.subscribe(System.out::println);
    }

    @Test
    void testFluxToListMono() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> personListMono = personFlux.collectList();

        personListMono.subscribe(System.out::println);
    }

    @Test
    void testFindPersonById() {
        Flux<Person> personFlux = personRepository.findAll();

        final Long id = 3L;

        Mono<Person> personMono = personFlux
                .filter(person -> person.getId().equals(id))
                .next();

        personMono.subscribe(System.out::println);
    }

    @Test
    void testFindPersonByIdBNotFound() {
        Flux<Person> personFlux = personRepository.findAll();

        final Long id = 5L;

        Mono<Person> personMono = personFlux
                .filter(person -> person.getId().equals(id))
                .next();

        personMono.subscribe(System.out::println);
    }

    @Test
    void testFindPersonByIdBWithException() {
        Flux<Person> personFlux = personRepository.findAll();

        final Long id = 10L;

        Mono<Person> personMono = personFlux
                .filter(person -> person.getId().equals(id))
                .single();

        personMono
                .onErrorReturn(Person.builder().build())
                .subscribe(System.out::println);
    }
}