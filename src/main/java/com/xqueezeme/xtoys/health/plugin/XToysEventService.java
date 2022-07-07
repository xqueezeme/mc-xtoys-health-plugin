package com.xqueezeme.xtoys.health.plugin;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class XToysEventService {
    private Logger logger;

    public XToysEventService(Logger logger) {
        this.logger = logger;
    }

    public void fire(String webhookId, XToysEvent xToysEvent)  {
        try {
            HttpClient client = HttpClient.newHttpClient();

            XToysEvent.Type type = xToysEvent.getType();
            final StringBuilder url = new StringBuilder("https://webhook.xtoys.app/?id=" + webhookId + "&action=" + type.name());

            url.append("&health=").append(URLEncoder.encode(String.valueOf(xToysEvent.getHealth()), StandardCharsets.UTF_8.toString()));
            url.append("&maxhealth=").append(URLEncoder.encode(String.valueOf(xToysEvent.getMaxHealth()), StandardCharsets.UTF_8.toString()));
            url.append("&player=").append(URLEncoder.encode(xToysEvent.getPlayerName(), StandardCharsets.UTF_8.toString()));

            switch (type) {
                case DAMAGE:
                case HEAL:
                    url.append("&amount=").append(URLEncoder.encode(String.valueOf(xToysEvent.getAmount()), StandardCharsets.UTF_8.toString()));
                    break;
            }
            URI uri = URI.create(url.toString());
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Accept", "*/*")
                    .GET()
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApplyAsync(HttpResponse::statusCode)
                    .thenAccept(code -> logger.info(code + " : " + url));
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getCause().getLocalizedMessage());
        }
    }

    private static class WebSocketClient implements WebSocket.Listener {
        private final CountDownLatch latch;

        public WebSocketClient(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println("onText received " + data);
            latch.countDown();
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.out.println("Bad day! " + webSocket.toString());
            WebSocket.Listener.super.onError(webSocket, error);
        }
    }
}
