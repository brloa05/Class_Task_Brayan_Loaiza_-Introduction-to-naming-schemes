import java.net.MalformedURLException;
import java.net.URL;

public class URLInfo {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://ldbn.escuelaing.edu.co:80/index.html?query=test#seccion1");

            System.out.println("Protocolo   : " + url.getProtocol());
            System.out.println("Authority   : " + url.getAuthority());
            System.out.println("Host        : " + url.getHost());
            System.out.println("Puerto      : " + url.getPort());
            System.out.println("Path        : " + url.getPath());
            System.out.println("Query       : " + url.getQuery());
            System.out.println("File        : " + url.getFile());
            System.out.println("Ref         : " + url.getRef());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
