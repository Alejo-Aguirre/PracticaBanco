package co.edu.banco.servidor;


public class ClienteCuenta {
    private String cedula;
    private String nombreCompleto;

    public ClienteCuenta(String cedula, String nombreCompleto) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}

