package com.sparqr.cx.gig.services;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import com.sparqr.cx.gig.services.pojos.Gig;
import com.sparqr.cx.gig.services.pojos.UpsertGig;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GigService {

  CompletableFuture<Gig> createOrUpdate(Long userId, Long entityId,
      UpsertGig gig);

  CompletableFuture<Void> delete(Long id);

  CompletableFuture<List<Gig>> list(Long userId);

  CompletableFuture<Boolean> validateById(Long id);

  CompletableFuture<Gig> findById(Long gigId);

  CompletableFuture<Void> updateStatus(Long id, GigStatusEnum status);
}
