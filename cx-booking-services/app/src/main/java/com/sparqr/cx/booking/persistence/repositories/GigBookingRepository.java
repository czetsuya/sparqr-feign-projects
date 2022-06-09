package com.sparqr.cx.booking.persistence.repositories;

import com.sparqr.cx.booking.api.commons.GigBookingStatusEnum;
import com.sparqr.cx.booking.persistence.entities.GigBookingEntity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GigBookingRepository extends JpaRepository<GigBookingEntity, Long> {

  @Query("SELECT COUNT(b) FROM GigBookingEntity b WHERE b.id=:id AND b.status IN (:statuses)")
  public Long countGigBookingByIdAndStatuses(@Param("id") Long id, @Param("statuses")
      Set<GigBookingStatusEnum> statuses);
}
