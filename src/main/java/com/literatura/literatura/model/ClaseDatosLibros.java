package com.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Datoslibros")
public class ClaseDatosLibros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Lenguaje lenguaje;

    private Integer descargas;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<ClasedatosAutores> autores = new ArrayList<>();


    public ClaseDatosLibros() {
    }

    public ClaseDatosLibros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        //this.lenguaje = Lenguaje.fromString(datosLibros.lenguaje().get(0));
        this.lenguaje = datosLibros.lenguaje() != null && !datosLibros.lenguaje().isEmpty() ?
                Lenguaje.fromString(datosLibros.lenguaje().get(0)) : null;
        this.descargas = datosLibros.descargas();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<ClasedatosAutores> getAutores() {
        return autores;
    }

    public void setAutores(List<ClasedatosAutores> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return null;
    }

    public void addAutor(ClasedatosAutores autor) {
    }
}
