package co.edu.banco.servidor;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PrincipalServidor {
    private EchoTCPServer server;
    private ArrayList<Usuario> ListaUsuarios;
    private ArrayList<ClienteCuenta> ListaClientesCuenta;
    private ArrayList<Cuenta> ListaCuentas;


    public static void main(String args[]) throws Exception
    {
        PrincipalServidor ps = new PrincipalServidor();
    }

    public PrincipalServidor() throws Exception
    {
        this.ListaUsuarios = new ArrayList();

        ListaUsuarios.add(new Usuario("Maria", "1234"));
        ListaUsuarios.add(new Usuario("Chucho", "5678"));
        ListaUsuarios.add(new Usuario("Ana", "2468"));
        ListaUsuarios.add(new Usuario("Jose", "1357"));

        ListaClientesCuenta = new ArrayList();
        ListaClientesCuenta.add(new ClienteCuenta("12345","Pepito Perez"));
        ListaClientesCuenta.add(new ClienteCuenta("77777","Martha Gomez"));
        ListaClientesCuenta.add(new ClienteCuenta("99999","Juan Lopez"));

        ListaCuentas = new ArrayList();
        ListaCuentas.add(new Cuenta("11111","04/03/2023", 50000.00,ListaClientesCuenta.get(0)));
        ListaCuentas.add(new Cuenta("22222","04/01/2023", 50000.00,ListaClientesCuenta.get(1)));
        ListaCuentas.add(new Cuenta("33333","04/02/2023", 50000.00,ListaClientesCuenta.get(2)));
        server = new EchoTCPServer(this);
        menu();
    }

    public void menu() throws Exception
    {
        JOptionPane.showMessageDialog(null, "En construcción. No acose!!!");

        server.init();
    }

    public boolean buscarUsuario(String login, String clave)
    {
        boolean encontrado=false;

        for(int i=0;i<ListaUsuarios.size() && encontrado==false;i++)
        {
            if (ListaUsuarios.get(i).getLogin().equalsIgnoreCase(login) && ListaUsuarios.get(i).getClave().equals(clave))
            {
                encontrado =true;
            }
        }
        return encontrado;
    }

    public String buscarCuenta(String numero)
    {
        String cuenta="";
        boolean encontrado=false;
        for(int i=0;i<ListaCuentas.size() && encontrado==false;i++)
        {
            if (ListaCuentas.get(i).getNumero().equals(numero))
            {
                cuenta = ListaCuentas.get(i).toString();
                encontrado = true;
            }
        }
        return cuenta;
    }

    public String actualizarSaldoCuenta(String numero, double s) {
        String res="";

        boolean encontrado=false;
        for(int i=0;i<ListaCuentas.size() && encontrado==false;i++)
        {
            if (ListaCuentas.get(i).getNumero().equals(numero))
            {
                ListaCuentas.get(i).setSaldo(s);
                res = ListaCuentas.get(i).toString();
                encontrado = true;
            }
        }
        return res;
    }
}
