package com.sparqr.cx.gig.persistence.repositories;

import com.sparqr.cx.gig.persistence.entities.GigPackageEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GigPackageRepository extends JpaRepository<GigPackageEntity, Long> {

  List<GigPackageEntity> findByGigId(Long gigId);
}
