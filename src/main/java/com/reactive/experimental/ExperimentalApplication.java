package com.reactive.experimental;

import com.reactive.experimental.DatabaseService.WordService;
import com.reactive.experimental.controller.MainController;
import com.reactive.experimental.units.Word;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class ExperimentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExperimentalApplication.class, args);
	}


}
