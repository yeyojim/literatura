package com.literatura.literatura;


import com.literatura.literatura.principal.Principal;
//import com.literatura.literatura.repository.AutorRepository;
//import com.literatura.literatura.repository.LibroRepository;
import com.literatura.literatura.repository.AutorRepository;
import com.literatura.literatura.repository.LibroRepository;
import com.literatura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

@Autowired

private LibroRepository libroRepository;

@Autowired
private AutorRepository autorRepository;

	@Autowired
	private LibroService libroService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}


@Override
	public void run(String... args) throws Exception {
Principal principal = new Principal(libroRepository, autorRepository, libroService);
	System.out.println("hola");

	principal.muestraElMenu();
	System.out.println(principal.obtenerDatos());


	}
}
