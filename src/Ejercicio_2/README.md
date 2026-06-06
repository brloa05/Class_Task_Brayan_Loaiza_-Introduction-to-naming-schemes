# Exercise 2 — Simple HTML Browser

A Java program that acts as a basic browser: you type a URL, it downloads the HTML and saves it to a file called `resultado.html`.

## What it does

- Asks the user to enter a URL
- Opens a connection using `url.openStream()`
- Reads the HTML line by line
- Saves everything to `resultado.html` in the current directory

## Code

```java
import java.io.*;
import java.net.*;

public class Browser {
    public static void main(String[] args) {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter URL: ");
            String direccion = userInput.readLine();

            URL url = new URL(direccion);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("resultado.html"));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();
            System.out.println("Saved to resultado.html");

        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading URL: " + e.getMessage());
        }
    }
}
```

## How to run

```bash
javac Browser.java
java Browser
```

When prompted, enter an HTTP URL (use `http://`, not `https://` to avoid SSL issues):
```
http://example.com
```

The file `resultado.html` will be created in the same folder where you run the command.

## Evidence

![img.png](../image/ej2_img.png)

The HTML is saved to resultado.html

![img_1.png](../image/ej2_img_1.png)

Opening the file in a browser

![img_2.png](../image/ej2_img_2.png)

![img_3.png](../image/ej2_img_3.png)
