package org.toy.catalogservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.toy.catalogservice.entity.CatalogEntity;

import java.util.Optional;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    Optional<CatalogEntity> findByProductId(String productId);
}
