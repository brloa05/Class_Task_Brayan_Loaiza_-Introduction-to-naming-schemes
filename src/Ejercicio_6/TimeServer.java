import java.net.*;
import java.util.Date;

public class TimeServer {

    private static final int PORT = 45000;

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("Servidor UDP de tiempo escuchando en el puerto " + PORT + "...");

        byte[] buffer = new byte[256];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
            System.out.println("Solicitud recibida de: " + clientAddress + ":" + clientPort);

            String timeMessage = "Hora del servidor: " + new Date().toString();
            byte[] responseBytes = timeMessage.getBytes();

            DatagramPacket response = new DatagramPacket(
                responseBytes,
                responseBytes.length,
                clientAddress,
                clientPort
            );
            socket.send(response);
            System.out.println("Enviado: " + timeMessage);
        }
    }
}
