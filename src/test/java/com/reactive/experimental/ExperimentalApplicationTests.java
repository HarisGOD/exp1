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
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.when;


@SpringBootTest
class ExperimentalApplicationTests {

	Word[] words = {new Word("SHIT", 0L), new Word("POOP", 1L)};
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
		System.out.println(wr.findAll().toStream().toList());
		System.out.println(wtc.get().uri("/service/words/list")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult());
	}

	@Test
	void testGet()
	{

		Flux<Word> word0 = Flux.just(words[0]);

		when(wr.findAllById(Collections.singleton(0L))).thenReturn(word0);
		System.out.println(wr.findAllById(Collections.singleton(0L)).toStream().toList());

		System.out.println(
			wtc.get().uri("/service/words/getById/0").exchange()
				.expectStatus().isOk()
				.expectBody()
				.returnResult()
		);
	}

}
