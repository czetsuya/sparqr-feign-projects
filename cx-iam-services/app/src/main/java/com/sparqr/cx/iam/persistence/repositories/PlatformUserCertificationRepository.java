package com.sparqr.cx.iam.persistence.repositories;

import com.sparqr.cx.iam.persistence.entities.PlatformUserCertificationEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserCertificationRepository extends JpaRepository<PlatformUserCertificationEntity, Long> {

  Optional<PlatformUserCertificationEntity> findByPlatformUserId(Long platformUserId);

  List<PlatformUserCertificationEntity> findByPlatformUserAlias(String userAlias);
}
