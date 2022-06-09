package com.sparqr.cx.iam.security;

import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {

  private SecurityUtils() {
  }

  public static Optional<String> getCurrentUser() {

    Optional<String> username = Optional.empty();
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      username = Optional.of(((UserDetails) authentication.getPrincipal()).getUsername());
    }

    return username;
  }
}