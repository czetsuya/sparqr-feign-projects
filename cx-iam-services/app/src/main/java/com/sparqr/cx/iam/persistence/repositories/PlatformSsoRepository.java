package com.sparqr.cx.iam.persistence.repositories;

import com.sparqr.cx.iam.persistence.entities.PlatformSsoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformSsoRepository extends JpaRepository<PlatformSsoEntity, Long> {

  Optional<PlatformSsoEntity> findByExternalRef(String externalRef);

  List<PlatformSsoEntity> findByPlatformUserId(Long platformUserId);
}
