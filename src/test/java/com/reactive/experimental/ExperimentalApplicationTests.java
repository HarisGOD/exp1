package com.reactive.experimental;

import com.reactive.experimental.DatabaseService.WordService;
import com.reactive.experimental.controller.MainController;
import com.reactive.experimental.repositories.WordRepo;
import com.reactive.experimental.units.Word;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExperimentalApplicationTests {
    Word[] words = {new Word("SHIT", 0L), new Word("POOP", 1L),new Word("FUCK",4L)};
    Flux<Word> fluxWords = Flux.just(words);

    WordRepo wr = Mockito.mock(WordRepo.class);
    WebTestClient wtc = WebTestClient
            .bindToController(
                    new MainController(
                    new WordService(wr)))
            .build();


    @Test
    void testList() {
        when(wr.findAll()).thenReturn(fluxWords);
        String pathSegment = "/service/words/list";
        String response = wtc.get().uri(pathSegment).exchange().expectBody().returnResult().toString();
        //Data in response like:
        // [{"name":"SHIT","id":0},{"name":"POOP","id":1}]
        // Its normal data, that part of normal result
        String normalData = this.normalData(words);
        //Normal "S" result like:
        String normalResult =this.normalResult(normalData,pathSegment);
        assertEquals(normalResult,response);
    }

    @Test
    void testGet() {
        Flux<Word> word0 = Flux.just(words[0]);
        when(wr.findAllById(Collections.singleton(0L))).thenReturn(word0);
        String pathSegment = "/service/words/getAllById/0";
        String response = wtc.get().uri(pathSegment)
                                    .exchange().expectBody().returnResult().toString();
        System.out.println(response);
        Word[] expectedData = {words[0]};
        String normalData = this.normalData(expectedData);
        String normalResult = this.normalResult(normalData,pathSegment);

        assertEquals(normalResult,response);
    }

    // вернёт строку с данными, получаемыми от контроллера, при нормальных условиях
    private String normalData(Word[] wordsArray){
        String normalData = "[";
        for (Word word: Arrays.stream(wordsArray).toList())
        {normalData+=word.toString()+",";}
        normalData+="]";
        normalData = normalData.replace(",]","]");
        return normalData;
    }
    // вернёт строку с ответом, получаемым от контроллера, при нормальных условиях
    private String normalResult(String normalData,String pathSegment){
        String normalResult =
                "\n" +
                        "> GET "+pathSegment+"\n" +
                        "> WebTestClient-Request-Id: [1]\n" +
                        "\n" +
                        "No content\n" +
                        "\n" +
                        "< 200 OK OK\n" +
                        "< Content-Type: [application/json]\n" +
                        "\n" +
                        normalData +
                        "\n"
                ;
        return normalResult;
    }

}
