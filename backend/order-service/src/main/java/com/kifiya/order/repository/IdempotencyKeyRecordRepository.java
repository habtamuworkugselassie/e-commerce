package com.kifiya.order.repository;

import com.kifiya.order.models.IdempotencyKeyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdempotencyKeyRecordRepository extends JpaRepository<IdempotencyKeyRecord, String> {
    Optional<IdempotencyKeyRecord> findByKeyId(String keyId);
}
