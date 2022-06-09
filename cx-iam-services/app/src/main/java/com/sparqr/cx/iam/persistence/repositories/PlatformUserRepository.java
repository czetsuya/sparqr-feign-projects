package com.sparqr.cx.iam.persistence.repositories;

import com.sparqr.cx.iam.persistence.entities.PlatformUserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUserEntity, Long> {

  Optional<PlatformUserEntity> findByEmail(String email);

  Optional<PlatformUserEntity> findByAlias(String alias);
}
