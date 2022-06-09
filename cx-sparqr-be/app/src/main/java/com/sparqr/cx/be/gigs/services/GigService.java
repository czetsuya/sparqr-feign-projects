package com.sparqr.cx.be.gigs.services;

import com.sparqr.cx.gig.api.dtos.inbound.UpdateGigStatusDto;
import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GigService {

  CompletableFuture<List<GigDto>> list(
      Long userId);

  CompletableFuture<GigDto> create(Long userId, UpsertGigDto gigDto);

  CompletableFuture<GigDto> update(Long userId, Long id, UpsertGigDto gigDto);

  CompletableFuture<Void> delete(Long userId, Long id);

  CompletableFuture<Boolean> validateById(Long id);

  CompletableFuture<Void> updateStatus(Long id, UpdateGigStatusDto updateGigStatusDto);

  CompletableFuture<GigDto> findById(Long gigId);
}
