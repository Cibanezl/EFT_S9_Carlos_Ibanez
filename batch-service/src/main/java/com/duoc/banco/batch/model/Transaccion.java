package com.duoc.banco.batch.model;

public class Transaccion {
    private String id;
    private String cuenta;
    private double monto;
    private String tipo;
    private String estado;

    public Transaccion() {}

    public Transaccion(String id, String cuenta, double monto, String tipo, String estado) {
        this.id = id;
        this.cuenta = cuenta;
        this.monto = monto;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}