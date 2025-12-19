package com.kifiya.identity_service.repository;

import com.kifiya.identity_service.repository.domain.BusinessInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessInfoRepository extends JpaRepository<BusinessInfo, java.util.UUID> {

    List<BusinessInfo> findByCustomerId(String customerId);

    Optional<BusinessInfo> findFirstByCustomerId(String customerId);
}

