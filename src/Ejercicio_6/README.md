# Exercise 6 — UDP Time Server

A client-server app using **UDP datagrams**. The client asks the server for the current time every 5 seconds. If the server is down, the client prints a timeout message and keeps trying — it never crashes.

## How it works

- The server listens for UDP packets on port **45000** and replies with the current date/time
- The client sends a request every 5 seconds and prints the response
- `setSoTimeout(3000)` limits the wait to 3 seconds — if no reply arrives, a `SocketTimeoutException` is caught and the client continues without stopping

## Code

### `TimeServer.java`

```java
import java.net.*;
import java.util.Date;

public class TimeServer {
    private static final int PORT = 45000;

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("UDP time server listening on port " + PORT);

        byte[] buffer = new byte[256];
        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            String time = "Server time: " + new Date();
            byte[] response = time.getBytes();

            socket.send(new DatagramPacket(
                response, response.length,
                request.getAddress(), request.getPort()
            ));
        }
    }
}
```

### `TimeClient.java`

```java
import java.net.*;

public class TimeClient {
    private static final String HOST = "localhost";
    private static final int PORT = 45000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Asking for time every 5 seconds... (Ctrl+C to stop)\n");
        while (true) {
            askForTime();
            Thread.sleep(5000);
        }
    }

    private static void askForTime() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(3000);

            byte[] request = "GET_TIME".getBytes();
            InetAddress address = InetAddress.getByName(HOST);
            socket.send(new DatagramPacket(request, request.length, address, PORT));

            byte[] buffer = new byte[256];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            System.out.println("[OK] " + new String(response.getData(), 0, response.getLength()));

        } catch (SocketTimeoutException e) {
            System.out.println("[TIMEOUT] Server not responding. Retrying in 5s...");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}
```

## How to run

Open **two terminals** in the `Ejercicio_6` folder:

**Terminal 1 — Server:**
```bash
javac TimeServer.java
java TimeServer
```

**Terminal 2 — Client:**
```bash
javac TimeClient.java
java TimeClient
```

**Testing fault tolerance:**
1. Start the client without the server → see `[TIMEOUT]` every 5 seconds
2. Start the server → the client starts receiving `[OK]` automatically
3. Stop the server (`Ctrl+C`) → the client goes back to `[TIMEOUT]` without crashing

## Evidence

![img.png](../image/ej6_img.png)
