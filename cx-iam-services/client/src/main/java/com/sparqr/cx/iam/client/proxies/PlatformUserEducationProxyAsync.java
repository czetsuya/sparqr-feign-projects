package com.sparqr.cx.iam.client.proxies;

import com.sparqr.cx.iam.api.dtos.commons.PlatformUserEducationDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformUserEducationProxyAsync {

  private final PlatformUserEducationProxy platformUserEducationProxy;

  @Async
  public CompletableFuture<List<PlatformUserEducationDto>> list(
      @Valid @NotBlank String alias) {
    return CompletableFuture.completedFuture(platformUserEducationProxy.list(alias));
  }

  @Async
  public CompletableFuture<PlatformUserEducationDto> create(
      @Valid @NotBlank String alias,
      @Valid PlatformUserEducationDto platformUserEducationDto) {
    return CompletableFuture.completedFuture(platformUserEducationProxy.create(alias, platformUserEducationDto));
  }

  @Async
  public CompletableFuture<PlatformUserEducationDto> update(
      @Valid @NotBlank String alias,
      @Valid  Long id,
      @Valid PlatformUserEducationDto platformUserEducationDto) {
    return CompletableFuture.completedFuture(platformUserEducationProxy.update(alias, id, platformUserEducationDto));
  }

  @Async
  public CompletableFuture<Void> delete(
      @Valid @NotBlank String alias,
      @Valid  Long id) {
    return CompletableFuture.completedFuture(platformUserEducationProxy.delete(alias, id));
  }
}
