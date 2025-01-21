package com.literatura.literatura.repository;

import com.literatura.literatura.model.ClasedatosAutores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<ClasedatosAutores, Long> {

    List<ClasedatosAutores> findByAnoNacimientoLessThanAndAnoDescesoGreaterThanEqual(Integer anoNacimiento, Integer anoDesceso);

    // Buscar autor por nombre
    Optional<ClasedatosAutores> findByNombreIgnoreCase(String nombre);

    // Buscar autores por año de nacimiento
    List<ClasedatosAutores> findByAnoNacimiento(Integer anoNacimiento);

    // Buscar autores por año de fallecimiento
    List<ClasedatosAutores> findByAnoDesceso(Integer anoDesceso);


}
