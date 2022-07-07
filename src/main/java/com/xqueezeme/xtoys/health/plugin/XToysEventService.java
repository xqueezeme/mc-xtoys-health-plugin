package com.xqueezeme.xtoys.health.plugin;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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
            final StringBuilder url = new StringBuilder("https://webhook.xtoys.app/?id=" +
                    URLEncoder.encode(webhookId, StandardCharsets.UTF_8.toString())
                    + "&action="
                    + URLEncoder.encode(type.name(), StandardCharsets.UTF_8.toString()));

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
        }
    }
}
