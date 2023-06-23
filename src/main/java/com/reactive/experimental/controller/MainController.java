package com.reactive.experimental.controller;

import com.reactive.experimental.DatabaseService.WordService;
import com.reactive.experimental.units.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/service/words")
public class MainController {
    private final WordService wordService;
    @Autowired
    public MainController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/list")
    public Flux<Word> list
            (@RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "10") Long End)
    {
        return wordService.list();
    }

    @PostMapping("/add")
    public Mono<Word> add(@RequestBody Word word){
        return wordService.addOne(word);
    }


    // DELETING

    @PostMapping("/removeById")
    public Mono<Void> remove(@RequestBody Word word){
        Long ID = word.getId();
        return wordService.removeOneById(ID);}

    @PostMapping("removeAllByName")
    public Mono<Void> removeAll(@RequestBody Word word){
        return wordService.removeAllByName(word.getName());
    }

    @PostMapping("removeAllByIdList")
    public Mono<Void> removeAllById(@RequestBody List<Word> words){
        Stream<Long> ids = words.stream().map(word ->{return word.getId();});
        return wordService.removeAllByIdList(ids.toList());
    }

    @PostMapping("removeAllInRange")
    public Mono<Void> removeAllInRange(@RequestBody List<Integer> interval){
        System.out.println(interval);
        long start = interval.get(0).longValue();
        long end = interval.get(1).longValue();
        List<Long> range = LongStream.range(start, end+1).boxed().toList();
        return wordService.removeAllByIdList(range);
    }
    // Добавил слова из тхт-шника
    @GetMapping("SuperSecret")
    public Flux<Word> adder(){
        try {
            List<String> content = Files.readAllLines(Paths.get("D:\\jdk\\projects\\experimental\\src\\main\\resources\\data\\words.txt"));
            Stream<Word> ab = content.stream().map(string -> {
                Word word = new Word(string);
                return word;
            });
            return wordService.addAll(ab.toList());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return Flux.empty();
    }
}
