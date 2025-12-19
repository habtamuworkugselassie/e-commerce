package com.kifiya.identity_service.repository;

import com.kifiya.identity_service.repository.domain.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KycRepository extends JpaRepository<Kyc, java.util.UUID> {

    List<Kyc> findByCustomerId(String customerId);

    Optional<Kyc> findFirstByCustomerId(String customerId);
}

