package org.toy.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toy.catalogservice.repository.CatalogRepository;
import org.toy.catalogservice.vo.ResponseCatalog;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public Iterable<ResponseCatalog> getAllCatalogs() {
        return ResponseCatalog.of(catalogRepository.findAll());
    }
}
