package com.example.ensayovolley;

import java.util.Date;

public class Noticia {
    private String fuente;
    private String autor;
    private String titulo;
    private String descripcion;
    private Date fecha;

    public Noticia(String fuente, String autor, String titulo, String descripcion, Date fecha) {
        this.fuente = fuente;
        this.autor = autor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getFuente() {
        return fuente;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }
}
