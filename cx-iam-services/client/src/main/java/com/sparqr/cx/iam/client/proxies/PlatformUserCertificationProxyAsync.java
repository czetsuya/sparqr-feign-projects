package com.sparqr.cx.iam.client.proxies;

import com.sparqr.cx.iam.api.dtos.commons.PlatformUserCertificationDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformUserCertificationProxyAsync {

  private final PlatformUserCertificationProxy platformUserCertificationProxy;

  @Async
  public CompletableFuture<List<PlatformUserCertificationDto>> list(
      @Valid @NotBlank String alias) {
    return CompletableFuture.completedFuture(platformUserCertificationProxy.list(alias));
  }

  @Async
  public CompletableFuture<PlatformUserCertificationDto> create(
      @Valid @NotBlank String alias,
      @Valid PlatformUserCertificationDto platformUserCertificationDto) {
    return CompletableFuture.completedFuture(
        platformUserCertificationProxy.create(alias, platformUserCertificationDto));
  }

  @Async
  public CompletableFuture<PlatformUserCertificationDto> update(
      @Valid @NotBlank String alias,
      @Valid  Long id,
      @Valid PlatformUserCertificationDto platformUserCertificationDto) {
    return CompletableFuture.completedFuture(
        platformUserCertificationProxy.update(alias, id, platformUserCertificationDto));
  }

  @Async
  public CompletableFuture<Void> delete(
      @Valid @NotBlank String alias,
      @Valid  Long id) {
    return CompletableFuture.completedFuture(platformUserCertificationProxy.delete(alias, id));
  }
}
