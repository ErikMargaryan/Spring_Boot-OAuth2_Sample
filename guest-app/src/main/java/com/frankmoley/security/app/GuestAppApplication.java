package com.frankmoley.security.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableOAuth2Client
public class GuestAppApplication {

	private static final String AUTH_TOKEN_URL = "/oauth/token";

	@Value("${landon.guest.service.url}")
	private String serviceUrl;

	public static void main(String[] args) {
		SpringApplication.run(GuestAppApplication.class, args);
	}

	@Bean
	public OAuth2RestTemplate restTemplate() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();

		List<String> scopes = new ArrayList<>();
		scopes.add("READ_ALL_GUESTS");
//		scopes.add("WRITE_GUEST");
//		scopes.add("UPDATE_GUEST");

		resource.setScope(scopes);
		resource.setClientId("api_audit");
		resource.setClientSecret("secret");
		resource.setGrantType("client_credentials");
		resource.setAccessTokenUri(serviceUrl + AUTH_TOKEN_URL);
		resource.setAuthenticationScheme(AuthenticationScheme.form);

		AccessTokenRequest requestToken = new DefaultAccessTokenRequest();
		return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(requestToken));
	}
}
