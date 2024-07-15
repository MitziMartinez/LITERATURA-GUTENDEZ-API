package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.principal;

import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model.Autor;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model.Datos;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model.DatosLibro;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model.Libro;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.repository.IAutorRepository;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.repository.ILibroRepository;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.service.ConsumoAPI;
import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoAPI consultaAPI = new ConsumoAPI();
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    List<Autor> autores;
    List<Libro> libros;
    private int opcionUsuario = -1;
    Scanner teclado = new Scanner(System.in);

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void consultaLibroPrincipal(){

        do{
            mostrarMenu();
            opcionUsuario = Integer.valueOf(teclado.nextLine());
            switch(opcionUsuario){
                case 1:
                    buscarLibroEnAPI();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutoresBuscados();
                    break;
                case 4:
                    mostrarAutoresPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("***PROGRAMA FINALIZADO ***");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while  (opcionUsuario != 0);


    }

    public void mostrarMenu(){
        System.out.println("""
                ******************** MENU ********************
                Seleccione una opción:             
                1- Buscar libro por titulo
                2- Listar libros registrados
                3- Listar autores registrados
                4- Listar autores vivos en determinado año
                5- listar libros por idioma
                    
                0- Salir
                
                ************************************************
                """);
    }

    public void muestraMenuIdiomas(){
        System.out.println("""
                Ingrese el idioma de los libros a buscar:
                es- Español
                en- Ingles
                de- Aleman
                fr- Frances
                """);
    }

    private void buscarLibroEnAPI(){
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String libroUsuario = teclado.nextLine();

        String busqueda = "?search=" + libroUsuario.replace(" ","+");
        var json = consultaAPI.obtenerDatos(URL_BASE + busqueda);

        var datos = conversor.obtenerDatos(json, Datos.class);

        DatosLibro datoslibro = datos.resultados().get(0);
        Libro libro = new Libro(datoslibro);
        Autor autor = new Autor().obtenerPrimerAutor(datoslibro);

        System.out.println(libro);
        guardarLibroConAutor(libro, autor);
    }

    private void guardarLibroConAutor(Libro libro, Autor autor){

        Optional<Autor> autorBuscado = autorRepository.findByNombreContains(autor.getNombre());

        if(autorBuscado.isPresent()){
            System.out.println("Favor de verificar - El autor ya existe");
            libro.setAutor(autorBuscado.get());
        } else {
            System.out.println("Autor nuevo registrado");
            autorRepository.save(autor);
            libro.setAutor(autor);
        }


        try {
            libroRepository.save(libro);
        } catch (Exception e) {

            System.out.println("Error al guardar el libro: " + e.getMessage());
        }
    }



    private void mostrarLibrosBuscados() {
        //Aqui se consulta nuestra base de datos postreSql
        libros = libroRepository.findAll();


        imprimeLibrosOrdenadosPorNombre(libros);
    }


    private void mostrarAutoresBuscados() {

        autores = autorRepository.findAll();


        imprimeAutoresOrdenadosPorNombre(autores);
    }


    private void mostrarAutoresPorAnio(){
        System.out.println("De que año deseas ver autores");
        Integer anio = Integer.valueOf(teclado.nextLine());

        autores = autorRepository
                .findByFechaDeNacimientoLessThanEqualAndFechaDeMuerteGreaterThanEqual
                        (anio, anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año");
        } else {
            imprimeAutoresOrdenadosPorNombre(autores);
        }
    }

    private void imprimeAutoresOrdenadosPorNombre(List<Autor> autores){
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void imprimeLibrosOrdenadosPorNombre(List<Libro> libros) {
        libros.stream()
                .sorted(Comparator.comparing(Libro::getNombreAutor))
                .forEach(System.out::println);
    }

    private void listarLibrosPorIdioma(){
        muestraMenuIdiomas();
        String idioma = teclado.nextLine();

        String claveIdioma;
        if (idioma.length() >= 2) {
            claveIdioma = idioma.substring(0, 2);
        } else {
            claveIdioma = idioma;
        }

        libros = libroRepository.findByIdiomasContains(claveIdioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma");
        } else {
            imprimeLibrosOrdenadosPorNombre(libros);
        }
    }

}
