package com.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true )
public record DatosGeneralesLibros(
       @JsonAlias ("results") List<DatosLibros> resultado
) {
}
