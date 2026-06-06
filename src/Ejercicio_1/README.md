# Exercise 1 — Reading URL Components

A simple Java program that breaks down a URL into its individual parts using the `java.net.URL` class.

## What it does

Given a URL like `http://ldbn.escuelaing.edu.co:80/index.html?query=test#seccion1`, the program prints each of the 8 components that Java can extract from it:

| Method | Returns |
|--------|---------|
| `getProtocol()` | `http` |
| `getAuthority()` | `ldbn.escuelaing.edu.co:80` |
| `getHost()` | `ldbn.escuelaing.edu.co` |
| `getPort()` | `80` |
| `getPath()` | `/index.html` |
| `getQuery()` | `query=test` |
| `getFile()` | `/index.html?query=test` |
| `getRef()` | `seccion1` |

## Code

```java
import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://ldbn.escuelaing.edu.co:80/index.html?query=test#seccion1");

            System.out.println("Protocol  : " + url.getProtocol());
            System.out.println("Authority : " + url.getAuthority());
            System.out.println("Host      : " + url.getHost());
            System.out.println("Port      : " + url.getPort());
            System.out.println("Path      : " + url.getPath());
            System.out.println("Query     : " + url.getQuery());
            System.out.println("File      : " + url.getFile());
            System.out.println("Ref       : " + url.getRef());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
```

## How to run

```bash
javac URLInfo.java
java URLInfo
```

## Evidence

![img.png](../image/ej1_img.png)
