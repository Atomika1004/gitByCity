package com.atomika.gitByCity;


import com.atomika.gitByCity.service.InitialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitByCityApplication {

	private static InitialService initialService;

	@Autowired
	public void setInitialService (InitialService initialService) {
		GitByCityApplication.initialService = initialService;
	}

	public static void main(String[] args) {
		SpringApplication.run(GitByCityApplication.class, args);
		initialService.initial();
	}

}
