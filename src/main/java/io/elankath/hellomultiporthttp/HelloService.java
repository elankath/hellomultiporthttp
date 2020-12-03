package io.elankath.hellomultiporthttp;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloService implements Closeable {
    private final int port;
    private final HttpServer server;
    private final HttpContext context;
    private final AtomicInteger callCount = new AtomicInteger();

    public HelloService(final int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.context = server.createContext("/");
        context.setHandler(this::handleRequest);
    }

    public void start() {
        System.out.println("Starting HelloService on port: " + port + "....");
        server.start();
        System.out.println("Started HelloService on port: " + port);
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        String response = "#" + callCount.incrementAndGet() + " Hello there sweetie!";
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        try (final OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public int getPort() {
        return port;
    }

    @Override
    public void close() throws IOException {
        if (server != null) {
            server.stop(30);
        }
    }
}
