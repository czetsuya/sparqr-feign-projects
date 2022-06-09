package com.sparqr.cx.gig.persistence.repositories;

import com.sparqr.cx.gig.persistence.entities.GigEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GigRepository extends JpaRepository<GigEntity, Long> {

  List<GigEntity> findByUserId(Long userId);
}