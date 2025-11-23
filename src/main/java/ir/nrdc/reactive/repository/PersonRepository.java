package ir.nrdc.reactive.repository;

import ir.nrdc.reactive.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> findById(Long id);

    Flux<Person> findAll();
}
