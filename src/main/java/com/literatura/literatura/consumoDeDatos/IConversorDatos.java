package com.literatura.literatura.consumoDeDatos;

public interface IConversorDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
