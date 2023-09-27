package co.edu.banco.cliente;

import java.io.IOException;
import javax.swing.JOptionPane;

public class PrincipalCliente {
    private EchoTCPClient cliente;
    private boolean logueado;

    public static void main(String args[]) throws Exception {
        PrincipalCliente pc = new PrincipalCliente();
        pc.iniciarSesion();
    }

    public PrincipalCliente() throws Exception {
        cliente = new EchoTCPClient();
        cliente.init();
        logueado = false;
    }

    public void iniciarSesion() throws IOException {
        String log, cla;
        String respuesta;

        do {
            log = JOptionPane.showInputDialog("Ingrese su Login: ");
            cla = JOptionPane.showInputDialog("Ingrese su clave: ");
            cliente.enviarMensaje("1;" + log + ";" + cla);
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
            opc = Integer.parseInt(JOptionPane.showInputDialog("BANCO LOS DESALMADOS \n\n"
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
}