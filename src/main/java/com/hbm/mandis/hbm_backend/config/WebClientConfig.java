package com.hbm.mandis.hbm_backend.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class WebClientConfig {
    @Value("${api.url}")
    private String ApiUrl;

    @Bean
    static SslContext sslContext() throws SSLException {
        return SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
    }

    @Bean("webClientApi")
    public WebClient webClientApi(SslContext sslContext) {
        return WebClient.builder()
                .baseUrl(ApiUrl)
                .clientConnector(clientConnector(sslContext))
                .build();
    }

    ReactorClientHttpConnector clientConnector(SslContext sslContext) {
        return new ReactorClientHttpConnector(
                HttpClient.create()
                        .secure(t -> t.sslContext(sslContext))
                        .compress(true)
                        .wiretap(true)
                        .resolver(DefaultAddressResolverGroup.INSTANCE)
        );
    }
}
