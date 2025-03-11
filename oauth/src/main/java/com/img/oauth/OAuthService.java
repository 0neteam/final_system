package com.img.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final OAuthClientRepository oAuthClientRepository;
  private final OAuthClientService oAuthClientService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtEncoder jwtEncoder;

  public boolean save(OAuthClient oAuthClient) {
    oAuthClient.setClientSecret( passwordEncoder.encode(oAuthClient.getClientSecret()) ); // 암호화 처리
    oAuthClientRepository.save(oAuthClient);
    return true;
  }

  public boolean getClient(String id, HttpServletResponse response, HttpSession session) {
    try {
      RegisteredClient registeredClient = oAuthClientService.findByClientId(id);

      JwtClaimsSet claimsSet = JwtClaimsSet.builder()
              .issuer(registeredClient.getId())
              .subject(registeredClient.getClientName())
              .issuedAt(Instant.now())
              .expiresAt(Instant.now().plus(1, ChronoUnit.MINUTES))
              .claim("role", registeredClient.getScopes())
              .build();

      JwtEncoderParameters parameters = JwtEncoderParameters.from(claimsSet);

      String access_token = jwtEncoder.encode(parameters).getTokenValue();
      Cookie cookie = new Cookie("access_token", access_token);
      cookie.setHttpOnly(true); // JavaScript에서 접근 불가
//      cookie.setSecure(true); // HTTPS에서만 전송
      cookie.setPath("/");
      cookie.setMaxAge(session.getMaxInactiveInterval());

      response.addCookie(cookie);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
