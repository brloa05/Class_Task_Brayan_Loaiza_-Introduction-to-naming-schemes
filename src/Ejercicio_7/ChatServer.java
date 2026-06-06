import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {

    public static void main(String[] args) {
        try {
            ChatImpl chatService = new ChatImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ChatService", chatService);

            System.out.println("Servidor de chat RMI iniciado en el puerto 1099.");
            System.out.println("Esperando mensajes de clientes...");
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
