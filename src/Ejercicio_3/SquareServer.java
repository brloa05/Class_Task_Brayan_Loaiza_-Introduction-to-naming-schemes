import java.io.*;
import java.net.*;

public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor esperando conexiones en el puerto 35000...");
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");
        } catch (IOException e) {
            System.err.println("Fallo al aceptar la conexión.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            try {
                double numero = Double.parseDouble(inputLine);
                double cuadrado = numero * numero;
                System.out.println("Número recibido: " + numero + " → Cuadrado: " + cuadrado);
                out.println("El cuadrado de " + numero + " es: " + cuadrado);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: " + inputLine);
                out.println("Error: '" + inputLine + "' no es un número válido.");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
