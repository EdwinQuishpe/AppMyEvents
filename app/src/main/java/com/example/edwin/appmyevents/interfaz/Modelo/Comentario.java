package com.example.edwin.appmyevents.interfaz.Modelo;

/**
 * Created by galoguartatanga on 1/24/18.
 */

public class Comentario {

    private  String nombre_p;
    private  String apellido_p;
    private  String comentario;
    private  String fecha_c;

    public String getNombre_p() {
        return nombre_p;
    }

    public void setNombre_p(String nombre_p) {
        this.nombre_p = nombre_p;
    }

    public String getApellido_p() {
        return apellido_p;
    }

    public void setApellido_p(String apellido_p) {
        this.apellido_p = apellido_p;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha_c() {
        return fecha_c;
    }

    public void setFecha_c(String fecha_c) {
        this.fecha_c = fecha_c;
    }
}
