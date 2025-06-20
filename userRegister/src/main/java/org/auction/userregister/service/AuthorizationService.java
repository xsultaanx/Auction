package org.auction.userregister.service;

import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class AuthorizationService {

//    private final KeycloakUserClientFactory userClientFactory;
    private final OAuth2AuthorizedClientManager manager;
    private final ClientRegistrationRepository registrationRepository;
    private final WebClient webClient;

//    @Value("${keycloak.user-client.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.user-client.client-secret}")
//    private String clientSecret;
//
//    @Value("${keycloak.auth-server-url}")
//    private String tokenUri;

//    public AuthorizationService(OAuth2AuthorizedClientManager manager,
//                       ClientRegistrationRepository registrationRepository,
//                                KeycloakUserClientFactory userClientFactory) {
//        this.manager = manager;
//        this.registrationRepository = registrationRepository;
//        this.webClient = WebClient.create();
//        this.userClientFactory = userClientFactory;
//    }

    public String login(String username, String password) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal(username) // любой уникальный ID, по которому потом делается refresh
                .attributes(attrs -> {
                    attrs.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, username);
                    attrs.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, password);
                })
                .build();

        OAuth2AuthorizedClient client = manager.authorize(request);
        if (client != null) {
            return client.getAccessToken().getTokenValue();
        }
        throw new RuntimeException("Failed to authorize");
    }

    // 🔄 Refresh token (будет вызван автоматически если истек токен, но можно и вручную)
    public String refreshToken(String username) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal(username)
                .build();

        OAuth2AuthorizedClient client = manager.authorize(request);
        if (client != null) {
            return client.getAccessToken().getTokenValue();
        }
        throw new RuntimeException("Failed to refresh token");
    }

//    public void logout(String refreshToken) {
//        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//        form.add("client_id", clientId);
//        form.add("client_secret", clientSecret);
//        form.add("refresh_token", refreshToken);
//
//        webClient.post()
//                .uri(tokenUri.replace("token", "logout"))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .bodyValue(form)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
//    }

//    //|tokenManager().getAccessToken()|admin-cli|
//    public AccessTokenResponse loginCli(String username, String password) {
//        Keycloak keycloak = userClientFactory.create(username, password);
//        return keycloak.tokenManager().getAccessToken();
//    }
}
