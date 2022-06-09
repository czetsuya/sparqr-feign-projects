package com.sparqr.cx.gig.services;

import com.sparqr.cx.gig.services.pojos.GigGalleryItem;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GigGalleryItemService {

  CompletableFuture<GigGalleryItem> createOrUpdate(Long gigId, Long entityId,
      GigGalleryItem galleryItem);

  CompletableFuture<Void> delete(Long id);

  CompletableFuture<List<GigGalleryItem>> list(Long userId);
}
