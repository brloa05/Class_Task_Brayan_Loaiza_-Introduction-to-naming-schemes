import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    void enviarMensaje(String mensaje, String emisor) throws RemoteException;
    String[] obtenerMensajes() throws RemoteException;
}
