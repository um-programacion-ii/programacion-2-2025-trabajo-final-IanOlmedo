package ar.edu.um.gestioneventos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configura un WebClient apuntando al proxy.
 * Usa la propiedad: app.proxy.base-url
 */
@Configuration
public class ProxyClientConfig {

    @Bean
    public WebClient proxyWebClient(@Value("${app.proxy.base-url}") String proxyBaseUrl) {
        return WebClient.builder()
                .baseUrl(proxyBaseUrl)
                .build();
    }
}