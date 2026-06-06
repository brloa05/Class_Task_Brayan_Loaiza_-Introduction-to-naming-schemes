import java.io.*;
import java.net.*;

public class TrigServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor trigonométrico esperando en el puerto 35000...");
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

        String funcionActual = "cos";
        System.out.println("Función inicial: coseno");

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("fun:")) {
                String nuevaFuncion = inputLine.substring(4).trim();
                if (nuevaFuncion.equals("sin") || nuevaFuncion.equals("cos") || nuevaFuncion.equals("tan")) {
                    funcionActual = nuevaFuncion;
                    System.out.println("Función cambiada a: " + funcionActual);
                    out.println("Función cambiada a: " + funcionActual);
                } else {
                    out.println("Error: función '" + nuevaFuncion + "' no reconocida. Use sin, cos o tan.");
                }
            } else {
                try {
                    double numero = Double.parseDouble(inputLine);
                    double resultado;

                    switch (funcionActual) {
                        case "sin": resultado = Math.sin(numero); break;
                        case "cos": resultado = Math.cos(numero); break;
                        case "tan": resultado = Math.tan(numero); break;
                        default:    resultado = Math.cos(numero);
                    }

                    System.out.println("Número: " + numero + " | Función: " + funcionActual + " | Resultado: " + resultado);
                    out.println(funcionActual + "(" + numero + ") = " + resultado);

                } catch (NumberFormatException e) {
                    out.println("Error: '" + inputLine + "' no es un número válido.");
                }
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
