package co.edu.banco.servidor;

public class Cuenta {
    private String numero;
    private String fechaApertura;
    private double saldo;
    private ClienteCuenta cc;

    public Cuenta(String numero, String fechaApertura, double saldo, ClienteCuenta cc) {
        this.numero = numero;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.cc=cc;
    }

    public String getNumero() {
        return numero;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public ClienteCuenta getCc() {
        return cc;
    }

    public String toString()
    {
        String cuenta = numero+";"+fechaApertura+";"+saldo+";"+cc.getNombreCompleto();
        return cuenta;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
