package com.example.crudmascotas;

public class Mascota {

    // Long permite que sea null cuando la mascota todavía no tiene ID
    private Long id;

    private String nombre;
    private String raza;
    private int edad;
    private String color;

    public Mascota() {
    }

    // Constructor para CREAR
    // No enviamos ID
    public Mascota(
            String nombre,
            String raza,
            int edad,
            String color
    ) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
    }

    // Constructor para mascotas que YA EXISTEN
    public Mascota(
            Long id,
            String nombre,
            String raza,
            int edad,
            String color
    ) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}