package com.kifiya.order.repository;

import com.kifiya.order.models.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(UUID id);

    @Query("select p from Product p where p.id in :ids")
    List<Product> findAllByIdsForUpdate(@Param("ids") List<UUID> ids);}

