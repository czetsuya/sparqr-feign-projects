package com.sparqr.cx.gig.services;

import com.sparqr.cx.gig.services.pojos.GigPackage;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GigPackageService {

  CompletableFuture<GigPackage> createOrUpdate(Long gigId, Long entityId, GigPackage gigPackage);

  CompletableFuture<Void> delete(Long id);

  CompletableFuture<List<GigPackage>> list(Long gigId);
}
