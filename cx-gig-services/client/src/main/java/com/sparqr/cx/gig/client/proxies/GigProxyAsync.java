package com.sparqr.cx.gig.client.proxies;

import static com.sparqr.cx.gig.api.EndpointConstants.PVAR_ID;

import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import com.sparqr.cx.gig.api.dtos.inbound.UpdateGigStatusDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class GigProxyAsync {

  private final GigProxy gigProxy;

  @Async
  public CompletableFuture<List<GigDto>> list(
      @Valid @NotNull Long userId) {
    return CompletableFuture.completedFuture(gigProxy.list(userId));
  }

  @Async
  public CompletableFuture<GigDto> create(
      @Valid @NotNull Long userId,
      @Valid @RequestBody UpsertGigDto gigDto) {
    return CompletableFuture.completedFuture(gigProxy.create(userId, gigDto));
  }

  @Async
  public CompletableFuture<GigDto> update(
      @Valid @NotNull Long userId,
      @Valid Long id,
      @Valid @RequestBody UpsertGigDto gigDto) {
    return CompletableFuture.completedFuture(gigProxy.update(userId, id, gigDto));
  }

  @Async
  public CompletableFuture<Void> delete(
      @Valid @NotNull Long userId,
      @Valid Long id) {
    return CompletableFuture.completedFuture(gigProxy.delete(userId, id));
  }

  @Async
  public CompletableFuture<Boolean> validateById(@Valid @NotNull Long id) {
    return CompletableFuture.completedFuture(gigProxy.validateById(id));
  }

  @Async
  public CompletableFuture<GigDto> findById(@Valid @NotNull Long gigId) {
    return CompletableFuture.completedFuture(gigProxy.findById(gigId));
  }

  @Async
  public CompletableFuture<Void> updateStatus(@PathVariable(name = PVAR_ID) @Valid @NotNull Long id,
      @RequestBody @Valid @NotNull UpdateGigStatusDto updateGigStatusDto) {
    return CompletableFuture.completedFuture(gigProxy.updateStatus(id, updateGigStatusDto));
  }
}
