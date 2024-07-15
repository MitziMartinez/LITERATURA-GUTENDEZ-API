package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura;

import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.principal.Principal;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.repository.IAutorRepository;

@SpringBootApplication
public class ScreenmatchLiteraturaApplication implements CommandLineRunner {
	@Autowired
	private ILibroRepository libroRepository;

	@Autowired
	private IAutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args){
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.consultaLibroPrincipal();
	}

}
