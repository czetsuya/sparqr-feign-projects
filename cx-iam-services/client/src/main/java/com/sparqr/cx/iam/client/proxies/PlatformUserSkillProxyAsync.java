package com.sparqr.cx.iam.client.proxies;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformUserSkillProxyAsync {

  private final PlatformUserSkillProxy platformUserSkillProxy;

  @Async
  public CompletableFuture<Set<String>> createOrUpdate(
      @Valid @NotBlank String alias,
      @Valid @NotEmpty Set<String> skills) {
    return CompletableFuture.completedFuture(platformUserSkillProxy.createOrUpdate(alias, skills));
  }

  @Async
  public CompletableFuture<Set<String>> getSkills(@Valid @NotBlank String userAlias) {
    return CompletableFuture.completedFuture(platformUserSkillProxy.getSkills(userAlias));
  }
}
