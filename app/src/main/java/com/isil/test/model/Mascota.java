package com.isil.test.model;

public class Mascota {
    String color, edad, nombre;
    public Mascota(){

    }
    public Mascota(String color, String edad, String nombre) {
        this.color = color;
        this.edad = edad;
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
