package com.literatura.literatura.service;

import com.literatura.literatura.model.ClaseDatosLibros;
import com.literatura.literatura.model.ClasedatosAutores;
import com.literatura.literatura.model.Lenguaje;
import com.literatura.literatura.repository.AutorRepository;
import com.literatura.literatura.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public List<ClaseDatosLibros> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
@Transactional
    public List<ClaseDatosLibros> listarLibros() {
        //return libroRepository.findAll();
        List<ClaseDatosLibros> libros = libroRepository.findAll();
        // Inicializar explícitamente la colección `autores`
        libros.forEach(libro -> libro.getAutores().size());
        return libros;
    }

    public List<ClasedatosAutores> listarAutores() {
        return autorRepository.findAll();
    }

    public List<ClasedatosAutores> listarAutoresVivosEnAno(Integer ano) {
        return autorRepository.findByAnoNacimientoLessThanAndAnoDescesoGreaterThanEqual(ano, ano);
    }

    public List<ClaseDatosLibros> listarLibrosPorIdioma(Lenguaje lenguaje) {
        return libroRepository.findByLenguaje(lenguaje);
    }
}

