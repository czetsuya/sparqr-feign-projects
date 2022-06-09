package com.sparqr.cx.gig.client.proxies;

import com.sparqr.cx.gig.api.dtos.commons.GigGalleryItemDto;
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
public class GigGalleryItemProxyAsync {

  private final GigGalleryItemProxy gigGalleryItemProxy;

  @Async
  public CompletableFuture<List<GigGalleryItemDto>> list(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId) {
    return CompletableFuture.completedFuture(gigGalleryItemProxy.list(userId, gigId));
  }

  @Async
  public CompletableFuture<GigGalleryItemDto> create(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId,
      @Valid @RequestBody GigGalleryItemDto gigGalleryItemDto) {
    return CompletableFuture.completedFuture(gigGalleryItemProxy.create(userId, gigId, gigGalleryItemDto));
  }

  @Async
  public CompletableFuture<GigGalleryItemDto> update(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId,
      @Valid Long id,
      @Valid @RequestBody GigGalleryItemDto gigGalleryItemDto) {
    return CompletableFuture.completedFuture(gigGalleryItemProxy.update(userId, gigId, id, gigGalleryItemDto));
  }


  @Async
  public CompletableFuture<Void> delete(
      @Valid @NotNull Long userId,
      @Valid @NotNull Long gigId,
      @Valid Long id) {
    return CompletableFuture.completedFuture(gigGalleryItemProxy.delete(userId, gigId, id));
  }
}
