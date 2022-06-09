package com.sparqr.cx.iam.persistence.repositories;

import com.sparqr.cx.iam.persistence.entities.PlatformUserEducationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserEducationRepository extends JpaRepository<PlatformUserEducationEntity, Long> {

  Optional<PlatformUserEducationEntity> findByPlatformUserId(Long platformUserId);

  List<PlatformUserEducationEntity> findByPlatformUserAlias(String userAlias);
}
