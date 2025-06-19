package br.com.alura.tabelafipe.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class ConsumoApi {

    private String urlBase;

    public ConsumoApi() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            this.urlBase = properties.getProperty("api.url.base");
        } catch (IOException e) {
            System.out.println("❌ Erro ao carregar configurações: " + e.getMessage());
            this.urlBase = "https://parallelum.com.br/fipe/api/v1/"; // fallback
        }
    }

    public String obterDados(String endpoint) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBase + endpoint))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Erro ao se conectar com a API: " + e.getMessage());
            return null;
        }
    }
}
