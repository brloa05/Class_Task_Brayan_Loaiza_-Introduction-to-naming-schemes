import java.io.*;
import java.net.*;

public class TrigClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Ingrese un número o cambie la función con 'fun:sin', 'fun:cos', 'fun:tan':");
        System.out.println("(La función por defecto es coseno)");

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Servidor: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
