package com.courier.tracking.hub.repository;

import com.courier.tracking.hub.entity.HubFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubFacilityRepository extends JpaRepository<HubFacility, Long> {
}
