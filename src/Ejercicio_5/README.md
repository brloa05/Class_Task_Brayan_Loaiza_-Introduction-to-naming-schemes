# Exercise 5 — HTTP Web Server

A web server built from scratch using Java TCP Sockets. It serves real files from a `www/` folder and handles multiple requests in sequence.

## What it does

- Listens on port **35000**
- Reads HTTP GET requests from the browser
- Serves HTML files and images from the `www/` folder
- Detects file type by extension and sets the correct `Content-Type` header
- Returns a **404** page if the requested file doesn't exist
- Keeps running after each request (handles multiple requests via `while(true)`)

## Project structure

```
Ejercicio_5/
├── HttpServer.java
└── www/
    ├── index.html
    └── pagina2.html
```

## Code

```java
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Web server listening on port 35000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) { clientSocket.close(); return; }

        System.out.println("Request: " + requestLine);
        while (in.ready()) in.readLine(); // discard remaining headers

        String[] parts = requestLine.split(" ");
        String filePath = parts.length > 1 ? parts[1] : "/";
        if (filePath.equals("/")) filePath = "/index.html";

        File file = new File("www" + filePath);

        if (file.exists() && !file.isDirectory()) {
            String contentType = getContentType(filePath);
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String headers = "HTTP/1.1 200 OK\r\nContent-Type: " + contentType
                    + "\r\nContent-Length: " + fileBytes.length + "\r\n\r\n";
            out.write(headers.getBytes());
            out.write(fileBytes);
        } else {
            String body = "<html><body><h1>404 - Not Found: " + filePath + "</h1></body></html>";
            String headers = "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\nContent-Length: "
                    + body.length() + "\r\n\r\n";
            out.write(headers.getBytes());
            out.write(body.getBytes());
        }

        out.flush();
        clientSocket.close();
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html") || path.endsWith(".htm")) return "text/html";
        if (path.endsWith(".css"))  return "text/css";
        if (path.endsWith(".js"))   return "application/javascript";
        if (path.endsWith(".png"))  return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif"))  return "image/gif";
        if (path.endsWith(".ico"))  return "image/x-icon";
        return "application/octet-stream";
    }
}
```

## How to run

```bash
cd src/Ejercicio_5
javac HttpServer.java
java HttpServer
```

Then open your browser and visit:

| URL | Result |
|-----|--------|
| `http://localhost:35000` | Main page (`index.html`) |
| `http://localhost:35000/pagina2.html` | Second page |
| `http://localhost:35000/notfound.html` | 404 error page |

## Evidence

Starting the server

![img.png](../image/ej5_img.png)

Browsing the pages

![img_1.png](../image/ej5_img_1.png)

![img_2.png](../image/ej5_img_2.png)

Requesting a file that doesn't exist

![img_3.png](../image/ej5_img_3.png)

Server request log

![img_4.png](../image/ej5_img_4.png)
