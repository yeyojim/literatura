package com.literatura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Datosautores")
public class ClasedatosAutores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anoNacimiento;
    private Integer anoDesceso;

    @ManyToMany(mappedBy = "autores")
    private List<ClaseDatosLibros> libros = new ArrayList<>();

    public ClasedatosAutores() {
    }

    public ClasedatosAutores(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombre();
        this.anoNacimiento = datosAutores.anoNacimiento();
        this.anoDesceso = datosAutores.anoDesceso();


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoDesceso() {
        return anoDesceso;
    }

    public void setAnoDesceso(Integer anoDesceso) {
        this.anoDesceso = anoDesceso;
    }

    public List<ClaseDatosLibros> getClaseDatosLibros() {
        return libros;
    }

    public void setClaseDatosLibros(List<ClaseDatosLibros> claseDatosLibros) {
        this.libros = claseDatosLibros;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anoNacimiento=" + anoNacimiento +
                ", anoDesceso=" + anoDesceso +
                ", claseDatosLibros=" + libros;
    }


}