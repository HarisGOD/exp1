package com.reactive.experimental;

import com.reactive.experimental.DatabaseService.WordService;
import com.reactive.experimental.controller.MainController;
import com.reactive.experimental.repositories.WordRepo;
import com.reactive.experimental.units.Word;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public class DataLoader {

    @MockBean
    WordService wordService;
// А КАК ТЕСТИРОВАТЬ ?? ?


    @Value("${experimental.data.location}")
    private String dataLocation;
    private final String filePath = new File("").getAbsolutePath();


    public Flux<Word> adder(){
        try {
            List<String> content = Files.readAllLines(Paths.get(filePath+dataLocation));
            //System.out.println(filePath+dataLocation);
            Stream<Word> fileData = content.stream().map(string -> {
                Word word = new Word(string);
                return word;
            });
            return wordService.addAll(fileData.toList());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return Flux.empty();
    }


}
