import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * VaultServer exposes HTTP endpoints to store and retrieve secrets.
 */
public final class VaultServer {
    private final Vault vault;

    /**
     * Creates a new server backed by the provided vault.
     *
     * @param vault secret storage to use
     */
    public VaultServer(Vault vault) {
        this.vault = vault;
    }

    /** Starts the server on port 8080. */
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/store", new StoreHandler());
        server.createContext("/get", new GetHandler());
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
    }

    private static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            respond(exchange, 200, "Vault server running\n");
        }
    }

    private class StoreHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                respond(exchange, 405, "Method Not Allowed\n");
                return;
            }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> params = queryToMap(body);
            String key = params.get("key");
            String value = params.get("value");
            if (key != null && value != null) {
                vault.storeSecret(key, value);
                respond(exchange, 200, "Stored\n");
            } else {
                respond(exchange, 400, "Missing key or value\n");
            }
        }
    }

    private class GetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equals(exchange.getRequestMethod())) {
                respond(exchange, 405, "Method Not Allowed\n");
                return;
            }
            String query = exchange.getRequestURI().getRawQuery();
            Map<String, String> params = queryToMap(query == null ? "" : query);
            String key = params.get("key");
            if (key != null) {
                String value = vault.getSecret(key);
                if (value.isEmpty()) {
                    respond(exchange, 404, "Not found\n");
                } else {
                    respond(exchange, 200, value + "\n");
                }
            } else {
                respond(exchange, 400, "Missing key\n");
            }
        }
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return result;
        }
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                result.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }
        return result;
    }
}
