package com.sparqr.cx.gig.services;

import com.sparqr.cx.gig.mappers.Persistence2ModelMapper;
import com.sparqr.cx.gig.mappers.Service2PersistenceMapper;
import com.sparqr.cx.gig.persistence.entities.GigPackageEntity;
import com.sparqr.cx.gig.persistence.repositories.GigPackageRepository;
import com.sparqr.cx.gig.persistence.repositories.GigRepository;
import com.sparqr.cx.gig.services.exceptions.GigNotFoundException;
import com.sparqr.cx.gig.services.pojos.GigPackage;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class GigPackageServiceImpl implements GigPackageService {

  private final GigRepository gigRepository;
  private final GigPackageRepository gigPackageRepository;
  private final Persistence2ModelMapper persistence2ModelMapper;
  private final Service2PersistenceMapper service2PersistenceMapper;

  @Override
  public CompletableFuture<GigPackage> createOrUpdate(Long gigId, Long entityId, GigPackage gigPackage) {

    Assert.notNull(gigPackage, "gigPackage cannot be null");
    if (Objects.isNull(entityId)) {
      Assert.notNull(gigId, "gigId cannot be null");
    }

    log.info("upsert gig gallery item for gig={}, entityId={}, and gigPackage={}", gigId, entityId, gigPackage);

    return CompletableFuture.supplyAsync(() -> {

      AtomicReference<GigPackage> atomicPojo = new AtomicReference<>();

      Optional.ofNullable(entityId)
          .ifPresentOrElse(gigPackageId -> {
            // update
            gigPackageRepository.findById(gigPackageId)
                .ifPresent(gigPackageEntity -> {
                  service2PersistenceMapper.toGigPackageEntity(gigPackage, gigPackageEntity);
                  gigPackageRepository.save(gigPackageEntity);
                  atomicPojo.set(persistence2ModelMapper.toGigPackage(gigPackageEntity));
                });
          }, () -> {

            // save
            GigPackageEntity newGigPackageEntity = service2PersistenceMapper.toGigPackageEntity(
                gigPackage);
            newGigPackageEntity.setGig(
                gigRepository.findById(gigId).orElseThrow(() -> new GigNotFoundException(gigId)));
            atomicPojo.set(
                persistence2ModelMapper.toGigPackage(gigPackageRepository.save(newGigPackageEntity)));
          });

      return atomicPojo.get();
    });
  }

  @Override
  public CompletableFuture<Void> delete(Long id) {

    log.info("removing gig package with id={}", id);

    return CompletableFuture.runAsync(() -> gigPackageRepository.deleteById(id));
  }

  @Override
  public CompletableFuture<List<GigPackage>> list(Long gigId) {

    log.info("requesting all packages for gig with id={}", gigId);

    return CompletableFuture.supplyAsync(
        () -> persistence2ModelMapper.toGigPackage(gigPackageRepository.findByGigId(gigId)));
  }
}
