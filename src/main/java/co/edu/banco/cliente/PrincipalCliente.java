package co.edu.banco.cliente;

import co.edu.banco.servidor.ClienteCuenta;
import co.edu.banco.servidor.Cuenta;
import co.edu.banco.servidor.Usuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;

public class PrincipalCliente {
    private EchoTCPClient cliente;
    private boolean logueado;

    public static void main(String args[]) throws Exception {
        PrincipalCliente pc = new PrincipalCliente();
        pc.mostrarMenuInicio();
    }

    public PrincipalCliente() throws Exception {
        cliente = new EchoTCPClient();
        cliente.init();
        logueado = false;
    }

    public void mostrarMenuInicio() throws IOException {
        int opcionInicio;
        do {
            opcionInicio = Integer.parseInt(JOptionPane.showInputDialog("BIENVENIDO AL BANCO\n\n"
                    + "1. Iniciar Sesión\n"
                    + "2. Crear Cliente\n"
                    + "3. Salir"));

            switch (opcionInicio) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    crearCuenta();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo de la aplicación");
                    cliente.getClientSideSocket().close();
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción Incorrecta");
                    break;
            }
        } while (opcionInicio != 3);
    }


    /**
     * iteracion 1 apertura de la cuenta
     */
    public void  crearCuenta() {

        String log, cla;
        String numeroCuenta,cedula,nombreCompleto;
        Date fechaApertura = new Date();
        double saldo;

        cedula = JOptionPane.showInputDialog("Ingrese su cedula: ");
        nombreCompleto = JOptionPane.showInputDialog("Ingrese su nombre: ");
        saldo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el saldo con el que desea abrir la cuenta: "));
        log = JOptionPane.showInputDialog("Ingrese su Login: ");
        cla = JOptionPane.showInputDialog("Ingrese su clave: ");

        numeroCuenta = generarNumeroDeCuentaUnico();

        // Obtener la fecha actual de creación
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaAperturaStr = sdf.format(fechaApertura);

        Usuario usuario = new Usuario(log,cla);
        ClienteCuenta clienteCuenta = new ClienteCuenta(cedula,nombreCompleto);
        Cuenta cuenta = new Cuenta(numeroCuenta,fechaAperturaStr,saldo,clienteCuenta);

        System.out.println(cuenta);

        // Formatear los datos de la cuenta, usuario y clienteCuenta para enviar al servidor
        String datosCuentaUsuarioCliente = "CREAR_CUENTA;" + // Indicar la solicitud de creación de cuenta, usuario y clienteCuenta
                usuario.getLogin() + ";" + usuario.getClave() + ";" +
                cuenta.getNumero() + ";" + cuenta.getFechaApertura() + ";" + cuenta.getSaldo() + ";" +
                clienteCuenta.getCedula() + ";" + clienteCuenta.getCedula();

        // Enviar los datos al servidor
        try {
            cliente.enviarMensaje(datosCuentaUsuarioCliente);
            String respuesta = cliente.leerMensaje();
            // Manejar la respuesta del servidor según tus necesidades
            JOptionPane.showMessageDialog(null, respuesta);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al enviar los datos al servidor: " + e.getMessage());
        }

    }

    public void iniciarSesion() throws IOException {
        String log, cla;
        String respuesta;
        do {
            log = JOptionPane.showInputDialog("Ingrese su Login: ");
            cla = JOptionPane.showInputDialog("Ingrese su clave: ");
            cliente.enviarMensaje("login;" + log + ";" + cla);
            respuesta = cliente.leerMensaje();
            if (respuesta.equals("ok")) {
                logueado = true;
                JOptionPane.showMessageDialog(null, "Bienvenido " + log);
                mostrarMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Login o clave incorrecta. Vuelva a intentarlo");
            }
        } while (!logueado);
    }

    public void mostrarMenu() throws IOException {
        int opc;
        String numeroC, numeroCD;
        String respuesta;
        String dinero;

        do {
            opc = Integer.parseInt(JOptionPane.showInputDialog("BANCO  \n\n"
                    + "1. Consultar saldo \n"
                    + "2. Consignar dinero \n"
                    + "3. Retirar efectivo \n"
                    + "4. Transferir efectivo \n"
                    + "5. Salir"));

            switch (opc) {
                case 1:
                    if (!logueado) {
                        JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                    } else {
                        numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta: ");
                        cliente.enviarMensaje(opc + ";" + numeroC);
                        respuesta = cliente.leerMensaje();
                        if (!respuesta.isEmpty())
                            JOptionPane.showMessageDialog(null, respuesta);
                        else
                            JOptionPane.showMessageDialog(null, "Cuenta no encontrada");
                    }
                    break;

                case 2:
                    if (!logueado) {
                        JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                    } else {
                        numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta: ");
                        dinero = JOptionPane.showInputDialog("Ingrese la cantidad a consignar: ");
                        cliente.enviarMensaje(opc + ";" + numeroC + ";" + dinero);
                        respuesta = cliente.leerMensaje();
                        if (!respuesta.isEmpty())
                            JOptionPane.showMessageDialog(null, "Nuevos datos: \n " + respuesta);
                        else
                            JOptionPane.showMessageDialog(null, "Cuenta no encontrada");
                    }
                    break;

                case 3:
                    if (!logueado) {
                        JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                    } else {
                        numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta: ");
                        dinero = JOptionPane.showInputDialog("Ingrese la cantidad a retirar: ");
                        cliente.enviarMensaje(opc + ";" + numeroC + ";" + dinero);
                        respuesta = cliente.leerMensaje();
                        if (!respuesta.isEmpty())
                            JOptionPane.showMessageDialog(null, "Nuevos datos: \n" + respuesta);
                        else
                            JOptionPane.showMessageDialog(null, "Cuenta no encontrada");
                    }
                    break;

                case 4:
                    if (!logueado) {
                        JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                    } else {
                        numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta origen: ");
                        numeroCD = JOptionPane.showInputDialog("Ingrese numero de cuenta destino: ");
                        dinero = JOptionPane.showInputDialog("Ingrese la cantidad a transferir: ");
                        cliente.enviarMensaje(opc + ";" + numeroC + ";" + numeroCD + ";" + dinero);
                        respuesta = cliente.leerMensaje();
                        if (!respuesta.isEmpty())
                            JOptionPane.showMessageDialog(null, "Nuevos datos: \n" + respuesta);
                        else
                            JOptionPane.showMessageDialog(null, "Cuenta origen o destino no encontrada");
                    }
                    break;

                case 5:
                    int cerrar = Integer.parseInt(JOptionPane.showInputDialog("Seguro desea cerrar la aplicación ? 1/2 "));
                    if (cerrar == 1) {
                        cliente.getClientSideSocket().close();
                        JOptionPane.showMessageDialog(null, "Cerrando aplicación");
                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción Incorrecta");
                    break;
            }

        } while (opc != 5);
    }

    private String generarNumeroDeCuentaUnico() {
        Random random = new Random();
        StringBuilder numeroCuentaBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            numeroCuentaBuilder.append(random.nextInt(10));
        }
        String numeroCuenta = numeroCuentaBuilder.toString();
        // Aquí puedes agregar lógica adicional para asegurarte de que el número sea único si es necesario
        return numeroCuenta;
    }
}