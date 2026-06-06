import java.io.*;
import java.net.*;
import java.nio.file.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor web escuchando en el puerto 35000...");
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto 35000.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            clientSocket.close();
            return;
        }

        System.out.println("Solicitud: " + requestLine);

        while (in.ready()) {
            in.readLine();
        }

        String[] parts = requestLine.split(" ");
        String filePath = parts.length > 1 ? parts[1] : "/";

        if (filePath.equals("/")) {
            filePath = "/index.html";
        }

        File file = new File("www" + filePath);

        if (file.exists() && !file.isDirectory()) {
            String contentType = getContentType(filePath);
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String headers = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + contentType + "\r\n"
                    + "Content-Length: " + fileBytes.length + "\r\n"
                    + "\r\n";

            out.write(headers.getBytes());
            out.write(fileBytes);
            System.out.println("Archivo enviado: " + file.getPath() + " (" + contentType + ")");
        } else {
            String body = "<html><body><h1>404 - Archivo no encontrado: " + filePath + "</h1></body></html>";
            String headers = "HTTP/1.1 404 Not Found\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + body.length() + "\r\n"
                    + "\r\n";
            out.write(headers.getBytes());
            out.write(body.getBytes());
            System.out.println("404 - No encontrado: " + filePath);
        }

        out.flush();
        clientSocket.close();
    }

    private static String getContentType(String filePath) {
        if (filePath.endsWith(".html") || filePath.endsWith(".htm")) return "text/html";
        if (filePath.endsWith(".css"))  return "text/css";
        if (filePath.endsWith(".js"))   return "application/javascript";
        if (filePath.endsWith(".png"))  return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        if (filePath.endsWith(".gif"))  return "image/gif";
        if (filePath.endsWith(".ico"))  return "image/x-icon";
        return "application/octet-stream";
    }
}
