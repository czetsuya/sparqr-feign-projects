package com.sparqr.cx.be.gigs.services;

import com.sparqr.cx.gig.api.dtos.inbound.UpdateGigStatusDto;
import com.sparqr.cx.gig.api.dtos.inbound.UpsertGigDto;
import com.sparqr.cx.gig.api.dtos.outbound.GigDto;
import com.sparqr.cx.gig.client.proxies.GigProxyAsync;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GigServiceImpl implements GigService {

  private final GigProxyAsync gigProxyAsync;

  @Override
  public CompletableFuture<List<GigDto>> list(
      Long userId) {

    return gigProxyAsync.list(userId);
  }

  @Override
  public CompletableFuture<GigDto> create(Long userId, UpsertGigDto gigDto) {

    return gigProxyAsync.create(userId, gigDto);
  }

  @Override
  public CompletableFuture<GigDto> update(Long userId, Long id, UpsertGigDto gigDto) {

    return gigProxyAsync.update(userId, id, gigDto);
  }

  @Override
  public CompletableFuture<Void> delete(Long userId, Long id) {

    return gigProxyAsync.delete(userId, id);
  }

  @Override
  public CompletableFuture<Boolean> validateById(Long id) {

    return gigProxyAsync.validateById(id);
  }

  @Override
  public CompletableFuture<Void> updateStatus(Long id, UpdateGigStatusDto updateGigStatusDto) {

    return gigProxyAsync.updateStatus(id, updateGigStatusDto);
  }

  @Override
  public CompletableFuture<GigDto> findById(Long gigId) {

    return gigProxyAsync.findById(gigId);
  }
}