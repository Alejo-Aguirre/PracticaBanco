package co.edu.banco.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class EchoTCPClient {
    private static final Scanner SCANNER = new Scanner(System.in);


    public static final String SERVER = "localhost";
    public static final int PORT = 3400;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    private Socket clientSideSocket;

    public Socket getClientSideSocket() {
        return clientSideSocket;
    }


    public EchoTCPClient() {
        System.out.println("Cliente ejecutandose...");
    }

    public void init() throws Exception {
        clientSideSocket = new Socket(SERVER, PORT);
        createStreams(clientSideSocket);
        protocol(clientSideSocket);
    }

    public void enviarMensaje(String cadena) throws IOException
    {
        toNetwork.println(cadena);
    }

    public String leerMensaje() throws IOException
    {
        String fromServer = fromNetwork.readLine();
        return fromServer;
    }

    public void protocol(Socket socket) throws Exception {
        System.out.print("Hola, me escucha? ");

        toNetwork.println("\n Soy el cliente");

        String fromServer = fromNetwork.readLine();
        System.out.println("El servidor dice: " + fromServer);
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
