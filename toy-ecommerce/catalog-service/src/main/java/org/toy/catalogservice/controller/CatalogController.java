package org.toy.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toy.catalogservice.service.CatalogService;
import org.toy.catalogservice.vo.ResponseCatalog;

@RestController
@RequestMapping("/catalog-service/")
@RequiredArgsConstructor
public class CatalogController {
    private final Environment environment;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return String.format("It's working in CatalogService on PORT %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<Iterable<ResponseCatalog>> getAllCatalogs() {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.getAllCatalogs());
    }
}
