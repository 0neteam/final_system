package com.img.oauth;

import com.nimbusds.jose.jwk.JWKSet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OAuthClientController {

  private final OAuthService oAuthService;
  private final JWKSet jwkSet;

  @GetMapping("/")
  public String home() {
    return "AUTHORIZATION";
  }

  @PostMapping("/getClient")
  public boolean getClient(@RequestParam("id") String id, HttpServletResponse response, HttpSession session) {
    return oAuthService.getClient(id, response, session);
  }

  @PostMapping("/addClient")
  public boolean save(@ModelAttribute OAuthClient oAuthClient) {
    return oAuthService.save(oAuthClient);
  }

  @GetMapping("/.well-known/jwks.json")
  public Map<String, Object> keys() {
    return jwkSet.toJSONObject();
  }

}
