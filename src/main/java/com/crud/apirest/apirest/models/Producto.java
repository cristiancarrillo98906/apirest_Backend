package com.crud.apirest.apirest.models;

/*
Primero, definimos la clase Producto, que representará los elementos en nuestro
archivo JSON:
* */

public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    // Constructor, getters y setters
    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}

