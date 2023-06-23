package com.reactive.experimental.repositories;

import com.reactive.experimental.units.Word;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface WordRepo extends ReactiveCrudRepository<Word,Long> {
    Mono<Void> deleteAllByName(String name);
    //Mono<Void> addAll(List<Word> words);
}
