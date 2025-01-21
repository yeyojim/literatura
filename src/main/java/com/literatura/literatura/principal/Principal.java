package com.literatura.literatura.principal;

import com.literatura.literatura.model.*;
import com.literatura.literatura.consumoDeDatos.ConsumoDeApi;
import com.literatura.literatura.consumoDeDatos.ConversorDatos;
import com.literatura.literatura.repository.AutorRepository;
import com.literatura.literatura.repository.LibroRepository;
import com.literatura.literatura.service.LibroService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public final class Principal {


    private ConsumoDeApi consumoDeApi = new ConsumoDeApi();
    private Scanner scanner = new Scanner(System.in);
    private ConversorDatos conversorDatos = new ConversorDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private LibroService libroService;

    public Principal(LibroRepository repository, AutorRepository autorRepository, LibroService libroService) {
        this.libroRepository = repository;
        this.autorRepository = autorRepository;
        this.libroService = libroService;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija su opcion:
                    
                    1 - Buscar libro por titulo.
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma.
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroYGuardar();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroYGuardar() {
        try {
            DatosGeneralesLibros datos = obtenerDatos();
            if (datos != null && datos.resultado() != null && !datos.resultado().isEmpty()) {
                for (DatosLibros datosLibro : datos.resultado()) {
                    // Verificar si el libro ya existe
                    Optional<ClaseDatosLibros> libroExistente =
                            libroRepository.findByTituloIgnoreCase(datosLibro.titulo());

                    if (libroExistente.isPresent()) {
                        System.out.println("El libro '" + datosLibro.titulo() +
                                "' ya existe en la base de datos.");
                        continue;
                    }

                    // Crear nuevo libro desde el record
                    ClaseDatosLibros nuevoLibro = new ClaseDatosLibros(datosLibro);

                    // Procesar autores desde el record
                    if (datosLibro.autores() != null) {
                        for (DatosAutores datosAutor : datosLibro.autores()) {
                            // Buscar si el autor ya existe
                            Optional<ClasedatosAutores> autorExistente =
                                    autorRepository.findByNombreIgnoreCase(datosAutor.nombre());

                            ClasedatosAutores autor;
                            if (autorExistente.isPresent()) {
                                autor = autorExistente.get();
                            } else {
                                // Crear nuevo autor desde el record
                                autor = new ClasedatosAutores(datosAutor);
                                autorRepository.save(autor);
                            }

                            nuevoLibro.addAutor(autor);
                        }
                    }

                    // Guardar libro
                    libroRepository.save(nuevoLibro);
                    System.out.println("Libro guardado exitosamente: " + nuevoLibro.getTitulo());
                    mostrarDetallesLibro(nuevoLibro);
                }
            } else {
                System.out.println("No se encontraron libros con ese título.");
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el libro: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void mostrarDetallesLibro(ClaseDatosLibros libro) {
        System.out.println("\nDetalles del libro guardado:");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Idioma: " + libro.getLenguaje());
        System.out.println("Descargas: " + libro.getDescargas());
        System.out.println("Autores:");
        for (ClasedatosAutores autor : libro.getAutores()) {
            System.out.println("  - Nombre: " + autor.getNombre());
            System.out.println("    Año nacimiento: " + autor.getAnoNacimiento());
            System.out.println("    Año fallecimiento: " + autor.getAnoDesceso());
        }
        System.out.println("------------------------");
    }

    private void listarLibrosRegistrados() {
        List<ClaseDatosLibros> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        System.out.println("\n=== Libros Registrados ===");
        for (ClaseDatosLibros libro : libros) {
            mostrarDetallesLibro(libro);
        }
    }

    private void listarAutoresRegistrados() {
        List<ClasedatosAutores> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        System.out.println("\n=== Autores Registrados ===");
        for (ClasedatosAutores autor : autores) {
            System.out.println("\nNombre: " + autor.getNombre());
            System.out.println("Año nacimiento: " + autor.getAnoNacimiento());
            System.out.println("Año fallecimiento: " + autor.getAnoDesceso());
            System.out.println("------------------------");
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Ingrese el año para buscar autores vivos: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        List<ClasedatosAutores> autoresVivos = autorRepository
                .findByAnoNacimientoLessThanAndAnoDescesoGreaterThanEqual(ano, ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + ano);
            return;
        }

        System.out.println("\n=== Autores vivos en el año " + ano + " ===");
        for (ClasedatosAutores autor : autoresVivos) {
            System.out.println("\nNombre: " + autor.getNombre());
            System.out.println("Año nacimiento: " + autor.getAnoNacimiento());
            System.out.println("Año fallecimiento: " + autor.getAnoDesceso());
            System.out.println("------------------------");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            Seleccione el idioma:
            1 - Español (es)
            2 - Inglés (en)
            3 - Portugués (pt)
            4 - Ruso (ru)
            5 - Frances (fr)
            """);

        int opcion = scanner.nextInt();
        scanner.nextLine();

        Lenguaje lenguajeSeleccionado;
        switch (opcion) {
            case 1: lenguajeSeleccionado = Lenguaje.ESPANOL; break;
            case 2: lenguajeSeleccionado = Lenguaje.INGLES; break;
            case 3: lenguajeSeleccionado = Lenguaje.PORTUGUES; break;
            case 4: lenguajeSeleccionado = Lenguaje.RUSO; break;
            case 5: lenguajeSeleccionado = Lenguaje.FRANCES; break;
            default:
                System.out.println("Opción inválida");
                return;
        }

        List<ClaseDatosLibros> libros = libroService.listarLibrosPorIdioma(lenguajeSeleccionado);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en " + lenguajeSeleccionado);
            return;
        }

        System.out.println("\n=== Libros en " + lenguajeSeleccionado + " ===");
        for (ClaseDatosLibros libro : libros) {
            mostrarDetallesLibro(libro);
        }
    }

    public DatosGeneralesLibros obtenerDatos() {
        System.out.println("Ingrese el título del libro a buscar:");
        var buscarLibro = scanner.nextLine();
        var json = consumoDeApi.obtenerDatos("https://gutendex.com/books/?search=" +
                buscarLibro.replace(" ", "+"));
        return conversorDatos.obtenerDatos(json, DatosGeneralesLibros.class);
    }
}












