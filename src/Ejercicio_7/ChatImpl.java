import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

    private final List<String> historial = new ArrayList<>();

    public ChatImpl() throws RemoteException {
        super();
    }

    @Override
    public void enviarMensaje(String mensaje, String emisor) throws RemoteException {
        String entrada = "[" + emisor + "]: " + mensaje;
        historial.add(entrada);
        System.out.println(entrada);
    }

    @Override
    public String[] obtenerMensajes() throws RemoteException {
        return historial.toArray(new String[0]);
    }
}
