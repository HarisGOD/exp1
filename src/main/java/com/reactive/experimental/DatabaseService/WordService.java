package com.reactive.experimental.DatabaseService;

import com.reactive.experimental.repositories.WordRepo;
import com.reactive.experimental.units.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class WordService {
    private final WordRepo wordRepo;
    @Autowired
    public WordService(WordRepo wordRepo){
        this.wordRepo = wordRepo;
    }

    public Mono<Word> getOneById(Long ID){return wordRepo.findById(ID);}

    public Flux<Word> getAllById(Long ID){return wordRepo.findAllById(Collections.singleton(ID));}
    public Flux<Word> getAllById(List<Long> listOfId){return wordRepo.findAllById(listOfId);}
    public Flux<Word> list(){return wordRepo.findAll();}
    public Mono<Word> addOne(Word word){return wordRepo.save(word);}
    public Mono<Void> removeOneById(Long id){return wordRepo.deleteById(id);}
    public Mono<Void> removeAllByName(String name){return wordRepo.deleteAllByName(name);}
    public Mono<Void> removeAllByIdList(List<Long> Ids){return wordRepo.deleteAllById(Ids);}
    public Flux<Word> addAll(List<Word> list){return wordRepo.saveAll(list);}


}
