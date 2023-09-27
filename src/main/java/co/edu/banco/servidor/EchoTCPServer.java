package co.edu.banco.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class EchoTCPServer {
    public static final int PORT = 3400;
    private ServerSocket listener;
    private Socket serverSideSocket;
    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;
    private PrincipalServidor serv;

    public EchoTCPServer(PrincipalServidor ps) {
        serv = ps;
        System.out.println("Servidor ejecutandose en el puerto : " + PORT);
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);
        serverSideSocket = listener.accept();
        createStreams(serverSideSocket);
        protocol(serverSideSocket);
        while (true)
        {
            String res = leerMensaje();
            responder(res);
        }
    }

    public String leerMensaje() throws IOException, Exception
    {
        String idTrans, log, cla, ced, numCue, saldo;
        createStreams(serverSideSocket);
        String cadena = fromNetwork.readLine();
        String resul[] = cadena.split(";");
        String respuesta="";
        String respuesta1="";
        double s;

        switch (resul[0])
        {
            case "1": log = resul[1];
                cla = resul[2];
                if (serv.buscarUsuario(log,cla)==true)
                    respuesta = "ok";
                break;


            case "2": respuesta= serv.buscarCuenta(resul[1]);
                break;

            case "3": respuesta= serv.buscarCuenta(resul[1]);
                if (respuesta!="")
                {
                    String cuen[] = respuesta.split(";");
                    s = Double.parseDouble(cuen[2])+Double.parseDouble(resul[2]);
                    respuesta = serv.actualizarSaldoCuenta(resul[1],s);
                }
                else
                {
                    respuesta="";
                }
                break;

            case "4": respuesta= serv.buscarCuenta(resul[1]);
                if (respuesta!="")
                {
                    String cuen[] = respuesta.split(";");
                    s = Double.parseDouble(cuen[2])-Double.parseDouble(resul[2]);
                    respuesta = serv.actualizarSaldoCuenta(resul[1],s);
                }
                else
                {
                    respuesta="";
                }
                break;

            case "5": respuesta= serv.buscarCuenta(resul[1]);
                respuesta1 = serv.buscarCuenta(resul[2]);
                if (respuesta!="" && respuesta1!="")
                {
                    String cuen[] = respuesta.split(";");
                    s = Double.parseDouble(cuen[2])-Double.parseDouble(resul[3]);
                    String resp = serv.actualizarSaldoCuenta(resul[1],s);

                    String cuen1[]=respuesta1.split(";");
                    s = Double.parseDouble(cuen1[2])+Double.parseDouble(resul[3]);
                    respuesta1 = serv.actualizarSaldoCuenta(resul[2],s);

                    respuesta ="Nuevos datos cuenta origen: " + resp +
                            "Nuevos datos cuenta destino: "+respuesta1;
                }
                else
                {
                    respuesta="";
                }
                break;

        }
        return respuesta;
    }

    public void protocol(Socket socket) throws Exception {
        String message = fromNetwork.readLine();
        System.out.println("El Cliente dice: " + message);

        String answer = "Si sr, lo escucho";

        toNetwork.println(answer);
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void responder(String res) {
        toNetwork.println(res);
    }

}
