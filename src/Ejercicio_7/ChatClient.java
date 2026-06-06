import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatInterface chat = (ChatInterface) registry.lookup("ChatService");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Ingresa tu nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Conectado al servidor. Escribe mensajes (escribe 'salir' para terminar):\n");

            while (true) {
                System.out.print("> ");
                String mensaje = scanner.nextLine();

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Desconectado.");
                    break;
                }

                if (!mensaje.trim().isEmpty()) {
                    chat.enviarMensaje(mensaje, nombre);
                }
            }

            scanner.close();

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
