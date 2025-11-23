package ir.nrdc.reactive.repository;

import ir.nrdc.reactive.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person person1 = new Person(1L, "f1", "l1");
    Person person2 = new Person(2L, "f2", "l2");
    Person person3 = new Person(3L, "f3", "l3");
    Person person4 = new Person(4L, "f4", "l4");

    @Override
    public Mono<Person> findById(Long id) {
        return findAll().filter(p -> p.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(person1, person2, person3, person4);
    }
}
