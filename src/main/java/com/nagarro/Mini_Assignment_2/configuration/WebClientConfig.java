package com.nagarro.Mini_Assignment_2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	// WebClient bean for API1
	@Bean(name = "api1WebClient")
	public WebClient api1WebClient() {
		ClientHttpConnector connector = new ReactorClientHttpConnector(
				HttpClient.create().responseTimeout(java.time.Duration.ofMillis(2000)));

		return WebClient.builder().clientConnector(connector).baseUrl("https://randomuser.me/api/")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
	}

	// WebClient bean for API2
	@Bean(name = "api2WebClient")
	public WebClient api2WebClient() {
		ClientHttpConnector connector = new ReactorClientHttpConnector(
				HttpClient.create().responseTimeout(java.time.Duration.ofMillis(1000)));

		return WebClient.builder().clientConnector(connector).baseUrl("https://api.nationalize.io")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
	}

	// WebClient bean for API3
	@Bean(name = "api3WebClient")
	public WebClient api3WebClient() {
		ClientHttpConnector connector = new ReactorClientHttpConnector(
				HttpClient.create().responseTimeout(java.time.Duration.ofMillis(1000)));

		return WebClient.builder().clientConnector(connector).baseUrl("https://api.genderize.io")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
	}
}
