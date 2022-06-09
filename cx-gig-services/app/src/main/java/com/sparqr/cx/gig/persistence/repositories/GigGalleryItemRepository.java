package com.sparqr.cx.gig.persistence.repositories;

import com.sparqr.cx.gig.persistence.entities.GigGalleryItemEntity;
import com.sparqr.cx.gig.services.pojos.Gig;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GigGalleryItemRepository extends JpaRepository<GigGalleryItemEntity, Long> {

  List<GigGalleryItemEntity> findByGig(Gig gig);

  List<GigGalleryItemEntity> findByGigId(Long gigId);
}
