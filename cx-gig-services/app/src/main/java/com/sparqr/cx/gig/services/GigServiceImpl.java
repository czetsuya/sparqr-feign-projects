package com.sparqr.cx.gig.services;

import com.sparqr.cx.gig.api.commons.GigStatusEnum;
import com.sparqr.cx.gig.mappers.Persistence2ModelMapper;
import com.sparqr.cx.gig.mappers.Service2PersistenceMapper;
import com.sparqr.cx.gig.persistence.entities.GigEntity;
import com.sparqr.cx.gig.persistence.repositories.GigRepository;
import com.sparqr.cx.gig.services.exceptions.GigNotActiveException;
import com.sparqr.cx.gig.services.exceptions.GigNotFoundException;
import com.sparqr.cx.gig.services.pojos.Gig;
import com.sparqr.cx.gig.services.pojos.UpsertGig;
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
public class GigServiceImpl implements GigService {

  private final GigRepository gigRepository;
  private final Service2PersistenceMapper service2PersistenceMapper;
  private final Persistence2ModelMapper persistence2ModelMapper;

  @Override
  public CompletableFuture<Gig> createOrUpdate(Long userId, Long entityId, UpsertGig gig) {

    Assert.notNull(gig, "gig cannot be null");
    if (Objects.isNull(entityId)) {
      Assert.notNull(userId, "userId cannot be null");
    }

    log.info("upsert gig for user with userId={}, entityId={}, and gig={}", userId, entityId, gig);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<Gig> atomicPojo = new AtomicReference<>();

      Optional.ofNullable(entityId)
          .ifPresentOrElse(gigId -> {
            // update
            gigRepository.findById(gigId)
                .ifPresent(gigEntity -> {
                  service2PersistenceMapper.toGigEntity(gig, gigEntity);
                  gigRepository.save(gigEntity);
                  atomicPojo.set(persistence2ModelMapper.toGig(gigEntity));
                });
          }, () -> {

            // save
            GigEntity newGigEntity = service2PersistenceMapper.toGigEntity(gig);
            newGigEntity.setUserId(userId);
            newGigEntity.setStatus(GigStatusEnum.DRAFT);
            atomicPojo.set(persistence2ModelMapper.toGig(gigRepository.save(newGigEntity)));
          });

      return atomicPojo.get();
    });
  }

  @Override
  public CompletableFuture<Void> delete(Long id) {

    log.info("removing gig with id={}", id);

    return CompletableFuture.runAsync(() -> gigRepository.deleteById(id));
  }

  @Override
  public CompletableFuture<List<Gig>> list(Long userId) {

    log.info("requesting all gigs for user with id={}", userId);

    return CompletableFuture.supplyAsync(() -> persistence2ModelMapper.toGig(gigRepository.findByUserId(userId)));
  }

  @Override
  public CompletableFuture<Boolean> validateById(Long id) {

    GigEntity gigEntity = gigRepository.findById(id)
        .orElseThrow(() -> new GigNotFoundException(id));

    return isValid(gigEntity);
  }

  @Override
  public CompletableFuture<Gig> findById(Long gigId) {

    return CompletableFuture.supplyAsync(() ->
        persistence2ModelMapper.toGig(
            gigRepository.findById(gigId)
                .orElseThrow(() -> new GigNotFoundException(gigId)))
    );
  }

  @Override
  public CompletableFuture<Void> updateStatus(Long id, GigStatusEnum status) {

    return CompletableFuture.runAsync(() -> {
      gigRepository.findById(id)
          .ifPresentOrElse(
              gigEntity -> {
                gigEntity.setStatus(status);
                gigRepository.save(gigEntity);
              },
              () -> {
                throw new GigNotFoundException(id);
              }
          );
    });
  }

  private CompletableFuture<Boolean> isValid(final GigEntity gigEntity) {

    // must be active
    if (!gigEntity.getStatus().equals(GigStatusEnum.ACTIVE)) {
      throw new GigNotActiveException(gigEntity.getId());
    }

    return CompletableFuture.completedFuture(Boolean.TRUE);
  }
}
