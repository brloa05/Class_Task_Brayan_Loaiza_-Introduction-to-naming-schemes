# Exercise 3 — TCP Square Server

A client-server application over TCP. The client sends a number and the server responds with its square.

## How it works

1. The server starts and waits for a connection on port **35000**
2. The client connects and sends numbers (one per line)
3. The server calculates `number × number` and sends back the result
4. If the input is not a valid number, the server returns an error message

## Code

### `SquareServer.java`

```java
import java.io.*;
import java.net.*;

public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Listening on port 35000...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            try {
                double number = Double.parseDouble(inputLine);
                double square = number * number;
                out.println("Square of " + number + " = " + square);
            } catch (NumberFormatException e) {
                out.println("Error: '" + inputLine + "' is not a valid number.");
            }
        }

        out.close(); in.close();
        clientSocket.close(); serverSocket.close();
    }
}
```

### `SquareClient.java`

```java
import java.io.*;
import java.net.*;

public class SquareClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 35000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter a number (Ctrl+C to quit):");
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
        }

        out.close(); in.close(); socket.close();
    }
}
```

## How to run

Open **two terminals** in the `Ejercicio_3` folder:

**Terminal 1 — Server:**
```bash
javac SquareServer.java
java SquareServer
```

**Terminal 2 — Client:**
```bash
javac SquareClient.java
java SquareClient
```

**Example interaction:**
```
> 5
Server: Square of 5.0 = 25.0
> 12
Server: Square of 12.0 = 144.0
```

## Evidence

![img.png](../image/ej3_img.png)
