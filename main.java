import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class main {

    public static void main(String[] args) throws IOException {
        // High execution server configuration on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/", new StaticFileHandler());
        server.setExecutor(null); 
        System.out.println("🚀 Server started successfully at http://localhost:8080");
        server.start();
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            
            if (path.equals("/")) {
                path = "/index.html";
            }

            File file = new File("." + path);

            if (!file.exists() || file.isDirectory()) {
                String response = "404 (Not Found): File miss-configured or missing.";
                exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                String contentType = "text/plain";
                if (path.endsWith(".html")) contentType = "text/html; charset=UTF-8";
                else if (path.endsWith(".css")) contentType = "text/css";
                else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) contentType = "image/jpeg";
                else if (path.endsWith(".png")) contentType = "image/png";

                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());

                // Using try-with-resources with large chunk allocation for fluid animations
                try (OutputStream os = exchange.getResponseBody();
                     FileInputStream fs = new FileInputStream(file)) {
                    byte[] buffer = new byte[8192]; 
                    int count;
                    while ((count = fs.read(buffer)) >= 0) {
                        os.write(buffer, 0, count);
                    }
                }
            }
        }
    }
}