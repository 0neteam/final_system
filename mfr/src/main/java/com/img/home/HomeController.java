package com.img.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "MFR";
  }

  @PostMapping("/me")
  public String me(@AuthenticationPrincipal Jwt jwt) {
    return jwt.getSubject();
  }

}
