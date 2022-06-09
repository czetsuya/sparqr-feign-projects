package com.sparqr.cx.gig.client.proxies;

import com.sparqr.cx.gig.api.dtos.commons.GigPackageDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class GigPackageProxyAsync {

  private final GigPackageProxy gigPackageProxy;

  @Async
  public CompletableFuture<List<GigPackageDto>> list(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId) {
    return CompletableFuture.completedFuture(gigPackageProxy.list(userId, gigId));
  }

  @Async
  public CompletableFuture<GigPackageDto> create(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId,
      @Valid @RequestBody GigPackageDto gigPackageDto) {
    return CompletableFuture.completedFuture(gigPackageProxy.create(userId, gigId, gigPackageDto));
  }

  @Async
  public CompletableFuture<GigPackageDto> update(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId,
      @Valid Long id,
      @Valid @RequestBody GigPackageDto gigPackageDto) {
    return CompletableFuture.completedFuture(gigPackageProxy.update(userId, gigId, id, gigPackageDto));
  }


  @Async
  public CompletableFuture<Void> delete(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId,
      @Valid Long id) {
    return CompletableFuture.completedFuture(gigPackageProxy.delete(userId, gigId, id));
  }
}
