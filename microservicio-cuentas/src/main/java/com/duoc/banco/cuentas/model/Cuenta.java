package com.duoc.banco.cuentas.model;

public class Cuenta {
    private String numeroCuenta;
    private String clienteId;
    private String tipoCuenta; // "Ahorro", "Corriente"
    private double saldo;
    private String estado; // "ACTIVA", "BLOQUEADA"

    public Cuenta() {}

    public Cuenta(String numeroCuenta, String clienteId, String tipoCuenta, double saldo, String estado) {
        this.numeroCuenta = numeroCuenta;
        this.clienteId = clienteId;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.estado = estado;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}