package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name="Books")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne()
    private Autor autor;

    private String nombreAutor;
    private String idiomas;
    private Double numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.nombreAutor = obtenerPrimerAutor(datosLibro).getNombre();
        this.idiomas = obtenerPrimerIdioma(datosLibro);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    // seters and getters


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas){
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    //metodos
    public Autor obtenerPrimerAutor(DatosLibro datosLibro){
        DatosAutor datosAutor = datosLibro.autor().get(0);
        return new Autor(datosAutor);
    }

    public String obtenerPrimerIdioma(DatosLibro datosLibro){
        String idioma = datosLibro.idiomas().toString();
        return idioma;
    }

    @Override
    public String toString(){
        return
                " ---------------------------------------------------"+
                "\n Titulo = " + titulo + "\'" +
                        "\n Autor =" + nombreAutor +
                        "\n Idiomas =" + idiomas+
                        "\n Numero de descargas= " + numeroDeDescargas +
                 "\n---------------------------------------------------"
                ;
    }
}


