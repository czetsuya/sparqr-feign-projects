package com.sparqr.cx.gig.persistence.config;

import com.sparqr.cx.gig.security.SecurityUtils;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    Optional<String> username = SecurityUtils.getCurrentUser();
    if (!username.isPresent()) {
      return Optional.of("SYSTEM");
    }

    return username;
  }
}
