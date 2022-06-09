package com.sparqr.cx.iam.persistence.repositories;

import com.sparqr.cx.iam.persistence.entities.PlatformUserCertificationEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserLanguageEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserLanguageRepository extends JpaRepository<PlatformUserLanguageEntity, Long> {

  Optional<PlatformUserLanguageEntity> findByPlatformUserId(Long platformUserId);

  List<PlatformUserLanguageEntity> findByPlatformUserAlias(String userAlias);
}
