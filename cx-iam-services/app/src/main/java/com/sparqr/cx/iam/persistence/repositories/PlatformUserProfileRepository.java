package com.sparqr.cx.iam.persistence.repositories;

import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import com.sparqr.cx.iam.persistence.entities.PlatformUserProfileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserProfileRepository extends JpaRepository<PlatformUserProfileEntity, Long> {

  Optional<PlatformUserProfileEntity> findByPlatformUser(PlatformUserEntity platformUser);

  Optional<PlatformUserProfileEntity> findByPlatformUserAlias(String alias);
}
