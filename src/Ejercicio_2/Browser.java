import java.io.*;
import java.net.*;

public class Browser {
    public static void main(String[] args) {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Ingrese la dirección URL: ");
            String direccion = userInput.readLine();

            URL url = new URL(direccion);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            FileWriter fileWriter = new FileWriter("resultado.html");
            BufferedWriter writer = new BufferedWriter(fileWriter);

            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }

            reader.close();
            writer.close();

            System.out.println("Contenido guardado en resultado.html");

        } catch (MalformedURLException e) {
            System.err.println("URL mal formada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer la URL: " + e.getMessage());
        }
    }
}
