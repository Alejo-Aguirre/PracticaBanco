package co.edu.banco.cliente;

import java.io.IOException;
import javax.swing.JOptionPane;

public class PrincipalCliente {
    private EchoTCPClient cliente;

    public static void main(String args[]) throws Exception
    {
        PrincipalCliente pc = new PrincipalCliente();
    }

    public PrincipalCliente() throws Exception
    {
        cliente = new EchoTCPClient();
        cliente.init();

        menu();
    }

    public void menu() throws IOException
    {
        int opc;
        int cerrar;
        String numeroC, numeroCD;
        String respuesta;
        String log, cla;
        boolean logueado=false;
        String dinero;
        do
        {
            opc = Integer.parseInt(JOptionPane.showInputDialog("BANCO LOS DESALMADOS \n\n"
                    + "1. Ingresar al banco \n"
                    + "2. Consultar saldo \n"
                    + "3. Consignar dinero \n"
                    + "4. Retirar efectivo \n"
                    + "5. Transferir efectivo \n"
                    + "6. Salir"));

            switch (opc)
            {
                case 1: log = JOptionPane.showInputDialog("Ingrese su Login: ");
                    cla = JOptionPane.showInputDialog("Ingrese su clave: ");
                    cliente.enviarMensaje(opc+";"+log+";"+cla);
                    respuesta = cliente.leerMensaje();
                    if (respuesta.equals("ok"))
                    {
                        logueado=true;
                        JOptionPane.showMessageDialog(null, "Bienvenido " + log);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Login o clave incorrecta. Vuelva a intentarlo");
                    }
                    break;

                case 2: if (!logueado)
                {
                    JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                }
                else
                {
                    numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta: ");
                    cliente.enviarMensaje(opc+";"+numeroC);
                    respuesta = cliente.leerMensaje();
                    if (respuesta!="")
                        JOptionPane.showMessageDialog(null,respuesta);
                    else
                        JOptionPane.showMessageDialog(null,"Cuenta no encontrada");
                }
                    break;

                case 3: if (!logueado)
                {
                    JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                }
                else
                {
                    numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta: ");
                    dinero = JOptionPane.showInputDialog("Ingrese la cantidad a consignar: ");
                    cliente.enviarMensaje(opc+";"+numeroC+";"+dinero);
                    respuesta = cliente.leerMensaje();
                    if (respuesta!="")
                        JOptionPane.showMessageDialog(null,"Nuevos datos: \n "+respuesta);
                    else
                        JOptionPane.showMessageDialog(null,"Cuenta no encontrada");
                }
                    break;

                case 4: if (!logueado)
                {
                    JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                }
                else
                {
                    numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta: ");
                    dinero = JOptionPane.showInputDialog("Ingrese la cantidad a retirar: ");
                    cliente.enviarMensaje(opc+";"+numeroC+";"+dinero);
                    respuesta = cliente.leerMensaje();
                    if (respuesta!="")
                        JOptionPane.showMessageDialog(null,"nuevos datos: \n"+ respuesta);
                    else
                        JOptionPane.showMessageDialog(null,"Cuenta no encontrada");
                }
                    break;

                case 5: if (!logueado)
                {
                    JOptionPane.showMessageDialog(null, "Logueese primero por favor!!!");
                }
                else
                {
                    numeroC = JOptionPane.showInputDialog("Ingrese numero de cuenta origen: ");
                    numeroCD = JOptionPane.showInputDialog("Ingrese numero de cuenta destino: ");
                    dinero = JOptionPane.showInputDialog("Ingrese la cantidad a transferir: ");
                    cliente.enviarMensaje(opc+";"+numeroC+";"+numeroCD+";"+dinero);
                    respuesta = cliente.leerMensaje();
                    if (respuesta!="")
                        JOptionPane.showMessageDialog(null,"nuevos datos: \n"+ respuesta);
                    else
                        JOptionPane.showMessageDialog(null,"Cuenta no origen o destino encontrada");
                }
                    break;

                case 6: cerrar = Integer.parseInt(JOptionPane.showInputDialog("Seguro desea cerrar la aplicación ? 1/2 "));
                    if (cerrar == 1)
                    {
                        cliente.getClientSideSocket().close();
                        JOptionPane.showMessageDialog(null, "Cerrando aplicación");
                    }
                    break;

                default: JOptionPane.showMessageDialog(null, "Opcion Incorrecta");
                    break;


            }

        }
        while(opc!=6);

    }

}
