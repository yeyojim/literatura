package com.literatura.literatura.model;

public enum Lenguaje {

    ESPANOL ("es"),
    INGLES("en"),
    PORTUGUES ("pt"),
    RUSO("ru"),
    FRANCES("fr");
    private String idioma;

    Lenguaje(String idioma) {
        this.idioma = idioma;
    }

    public static Lenguaje fromString(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.idioma.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("El idioma no fue encontrada: " + text);
    }
}
