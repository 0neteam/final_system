package com.img.oauth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="oauthclient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthClient {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String clientId;
  private String clientSecret;
  private String redirectUri;
  private String postLogoutRedirectUri;
  private boolean requireAuthorizationConsent;

}
