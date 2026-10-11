package com.duoc.banco.clientes.model;

public class Cliente {
    private String id;
    private String rut;
    private String nombre;
    private String email;
    private String tipoPerfil; // ej: "VIP", "Estándar"

    public Cliente() {}

    public Cliente(String id, String rut, String nombre, String email, String tipoPerfil) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.tipoPerfil = tipoPerfil;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoPerfil() { return tipoPerfil; }
    public void setTipoPerfil(String tipoPerfil) { this.tipoPerfil = tipoPerfil; }
}