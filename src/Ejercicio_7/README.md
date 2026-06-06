# Exercise 7 — RMI Chat

A chat application using **Java RMI (Remote Method Invocation)**. Clients send messages to a server by calling a remote method directly — no manual message parsing, no raw sockets. Java handles the networking transparently.

## How it works

1. `ChatInterface` defines the remote contract (methods callable over the network)
2. `ChatImpl` implements the logic (stores messages, prints them to the server console)
3. `ChatServer` registers the service in the RMI registry on port **1099**
4. `ChatClient` looks up the service and calls `enviarMensaje()` as if it were a local method

## Files

```
Ejercicio_7/
├── ChatInterface.java   → Remote interface
├── ChatImpl.java        → Service implementation
├── ChatServer.java      → Starts the RMI registry and registers the service
└── ChatClient.java      → Connects and sends messages
```

## Code

### `ChatInterface.java`

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    void enviarMensaje(String mensaje, String emisor) throws RemoteException;
    String[] obtenerMensajes() throws RemoteException;
}
```

### `ChatImpl.java`

```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

    private final List<String> history = new ArrayList<>();

    public ChatImpl() throws RemoteException { super(); }

    @Override
    public void enviarMensaje(String mensaje, String emisor) throws RemoteException {
        String entry = "[" + emisor + "]: " + mensaje;
        history.add(entry);
        System.out.println(entry);
    }

    @Override
    public String[] obtenerMensajes() throws RemoteException {
        return history.toArray(new String[0]);
    }
}
```

### `ChatServer.java`

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ChatImpl service = new ChatImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ChatService", service);
            System.out.println("RMI chat server running on port 1099...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### `ChatClient.java`

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatInterface chat = (ChatInterface) registry.lookup("ChatService");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Your name: ");
            String name = scanner.nextLine();
            System.out.println("Connected! Type messages (type 'exit' to quit):\n");

            while (true) {
                System.out.print("> ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;
                if (!msg.trim().isEmpty()) chat.enviarMensaje(msg, name);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## How to run

**Step 1 — Compile everything:**
```bash
cd src/Ejercicio_7
javac ChatInterface.java ChatImpl.java ChatServer.java ChatClient.java
```

**Terminal 1 — Server:**
```bash
java ChatServer
```

**Terminal 2+ — Client(s):**
```bash
java ChatClient
```

You can open as many client terminals as you want — all messages show up on the server console.

**Example:**
```
Your name: Juan
Connected! Type messages (type 'exit' to quit):

> Hello from the client!
> Anyone there?
> exit
```

**Server console:**
```
RMI chat server running on port 1099...
[Juan]: Hello from the client!
[Juan]: Anyone there?
```

## Evidence

Starting the server

![img.png](../image/ej7_img.png)

Client 1 connected

![img_1.png](../image/ej7_img_1.png)

Server receiving messages

![img_2.png](../image/ej7_img_2.png)

Client 2 connected

![img_3.png](../image/ej7_img_3.png)

Server with messages from both clients

![img_4.png](../image/ej7_img_4.png)
