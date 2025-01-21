package com.literatura.literatura.repository;

import com.literatura.literatura.model.ClaseDatosLibros;
import com.literatura.literatura.model.ClasedatosAutores;
import com.literatura.literatura.model.Lenguaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LibroRepository extends JpaRepository<ClaseDatosLibros, Long> {



    // Buscar libros por título

    List<ClaseDatosLibros> findByTituloContainingIgnoreCase(String titulo);

    // Buscar por lenguaje usando el enum
    List<ClaseDatosLibros> findByLenguaje(Lenguaje lenguaje);

    // Buscar libros con más descargas que un número específico
    List<ClaseDatosLibros> findByDescargasGreaterThan(Integer descargas);

    // Buscar libro por título exacto
    Optional<ClaseDatosLibros> findByTituloIgnoreCase(String titulo);

    // Ordenar libros por número de descargas
    List<ClaseDatosLibros> findAllByOrderByDescargasDesc();



}
