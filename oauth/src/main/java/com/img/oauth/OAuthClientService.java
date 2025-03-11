package com.img.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthClientService {

  private final OAuthClientRepository oAuthClientRepository;
  private String msg = "Client not Found Exception: ";

  public RegisteredClient findById(String id) {
    UUID uuid = UUID.fromString(id);
    OAuthClient oAuthClient = oAuthClientRepository.findById(uuid)
        .orElseThrow(() -> new IllegalArgumentException(msg + uuid));
    return loadClientByResult(oAuthClient);
  }

  public RegisteredClient findByClientId(String clientId) {
    OAuthClient oAuthClient = oAuthClientRepository.findByClientId(clientId)
        .orElseThrow(() -> new IllegalArgumentException(msg + clientId));
    return loadClientByResult(oAuthClient);
  }

  private RegisteredClient loadClientByResult(OAuthClient oAuthClient) {
    return RegisteredClient
        .withId(oAuthClient.getId().toString())
        .clientId(oAuthClient.getClientId())
        .clientSecret(oAuthClient.getClientSecret())
//            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(oAuthClient.getRedirectUri())
        .postLogoutRedirectUri(oAuthClient.getPostLogoutRedirectUri())
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
        .scope("read")
        .scope("write")
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(oAuthClient.isRequireAuthorizationConsent()).build())
        .build();
  }

}
