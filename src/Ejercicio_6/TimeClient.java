import java.net.*;

public class TimeClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 45000;
    private static final int TIMEOUT_MS = 3000;
    private static final int INTERVAL_MS = 5000;

    public static void main(String[] args) {
        System.out.println("Cliente UDP iniciado. Consultando hora cada 5 segundos...");
        System.out.println("(Presiona Ctrl+C para detener)\n");

        while (true) {
            consultarHora();
            try {
                Thread.sleep(INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static void consultarHora() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);

            byte[] requestBytes = "GET_TIME".getBytes();
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            DatagramPacket request = new DatagramPacket(
                requestBytes,
                requestBytes.length,
                serverAddress,
                SERVER_PORT
            );
            socket.send(request);

            byte[] buffer = new byte[256];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            String timeMessage = new String(response.getData(), 0, response.getLength());
            System.out.println("[OK] " + timeMessage);

        } catch (SocketTimeoutException e) {
            System.out.println("[TIMEOUT] Servidor no responde. Reintentando en 5 segundos...");
        } catch (Exception e) {
            System.out.println("[ERROR] No se pudo conectar al servidor: " + e.getMessage());
        }
    }
}
