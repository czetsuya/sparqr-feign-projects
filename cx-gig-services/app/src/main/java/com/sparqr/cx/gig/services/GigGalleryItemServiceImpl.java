package com.sparqr.cx.gig.services;

import com.sparqr.cx.gig.mappers.Persistence2ModelMapper;
import com.sparqr.cx.gig.mappers.Service2PersistenceMapper;
import com.sparqr.cx.gig.persistence.entities.GigGalleryItemEntity;
import com.sparqr.cx.gig.persistence.repositories.GigGalleryItemRepository;
import com.sparqr.cx.gig.persistence.repositories.GigRepository;
import com.sparqr.cx.gig.services.exceptions.GigNotFoundException;
import com.sparqr.cx.gig.services.pojos.GigGalleryItem;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class GigGalleryItemServiceImpl implements GigGalleryItemService {

  private final GigRepository gigRepository;
  private final GigGalleryItemRepository gigGalleryItemRepository;
  private final Persistence2ModelMapper persistence2ModelMapper;
  private final Service2PersistenceMapper service2PersistenceMapper;

  @Override
  public CompletableFuture<GigGalleryItem> createOrUpdate(Long gigId, Long entityId, GigGalleryItem gigGalleryItem) {

    Assert.notNull(gigGalleryItem, "gigGalleryItem cannot be null");
    if (Objects.isNull(entityId)) {
      Assert.notNull(gigId, "gigId cannot be null");
    }

    log.info("upsert gig gallery item for gig={}, entityId={}, and galleryItem={}", gigId, entityId, gigGalleryItem);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<GigGalleryItem> atomicPojo = new AtomicReference<>();

      Optional.ofNullable(entityId)
          .ifPresentOrElse(gigGalleryItemId -> {
            // update
            gigGalleryItemRepository.findById(gigGalleryItemId)
                .ifPresent(gigGalleryItemEntity -> {
                  service2PersistenceMapper.toGigGalleryItemEntity(gigGalleryItem, gigGalleryItemEntity);
                  gigGalleryItemRepository.save(gigGalleryItemEntity);
                  atomicPojo.set(persistence2ModelMapper.toGigGalleryItem(gigGalleryItemEntity));
                });
          }, () -> {

            // save
            GigGalleryItemEntity newGigGalleryItemEntity = service2PersistenceMapper.toGigGalleryItemEntity(
                gigGalleryItem);
            newGigGalleryItemEntity.setGig(
                gigRepository.findById(gigId).orElseThrow(() -> new GigNotFoundException(gigId)));
            atomicPojo.set(
                persistence2ModelMapper.toGigGalleryItem(gigGalleryItemRepository.save(newGigGalleryItemEntity)));
          });

      return atomicPojo.get();
    });
  }

  @Override
  public CompletableFuture<Void> delete(Long id) {

    log.info("removing gallery item with id={}", id);

    return CompletableFuture.runAsync(() -> gigGalleryItemRepository.deleteById(id));
  }

  @Override
  public CompletableFuture<List<GigGalleryItem>> list(Long gigId) {

    log.info("requesting all gallery items for gig with id={}", gigId);

    return CompletableFuture.supplyAsync(
        () -> persistence2ModelMapper.toGigGalleryItem(gigGalleryItemRepository.findByGigId(gigId)));
  }
}
