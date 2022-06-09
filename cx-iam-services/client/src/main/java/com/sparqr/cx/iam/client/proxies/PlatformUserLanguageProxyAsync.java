package com.sparqr.cx.iam.client.proxies;

import com.sparqr.cx.iam.api.dtos.commons.PlatformUserLanguageDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformUserLanguageProxyAsync {

  private final PlatformUserLanguageProxy platformUserLanguageProxy;

  @Async
  public CompletableFuture<List<PlatformUserLanguageDto>> list(
      @Valid @NotBlank String alias) {
    return CompletableFuture.completedFuture(platformUserLanguageProxy.list(alias));
  }

  @Async
  public CompletableFuture<PlatformUserLanguageDto> create(
      @Valid @NotBlank String alias,
      @Valid PlatformUserLanguageDto platformUserLanguageDto) {
    return CompletableFuture.completedFuture(platformUserLanguageProxy.create(alias, platformUserLanguageDto));
  }

  @Async
  public CompletableFuture<PlatformUserLanguageDto> update(
      @Valid @NotBlank String alias,
      @Valid  Long id,
      @Valid PlatformUserLanguageDto platformUserLanguageDto) {
    return CompletableFuture.completedFuture(platformUserLanguageProxy.update(alias, id, platformUserLanguageDto));
  }

  @Async
  public CompletableFuture<Void> delete(@Valid @NotBlank String alias,
      @Valid  Long id) {
    return CompletableFuture.completedFuture(platformUserLanguageProxy.delete(alias, id));
  }
}
