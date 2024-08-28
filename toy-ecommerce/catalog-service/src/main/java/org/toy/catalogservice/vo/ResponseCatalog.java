package org.toy.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.toy.catalogservice.entity.CatalogEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private LocalDateTime createdAt;

    public static ResponseCatalog of(CatalogEntity catalogEntity) {
        return ResponseCatalog.builder()
                .productId(catalogEntity.getProductId())
                .productName(catalogEntity.getProductName())
                .unitPrice(catalogEntity.getUnitPrice())
                .stock(catalogEntity.getStock())
                .createdAt(catalogEntity.getCreatedAt())
                .build();
    }

    public static Iterable<ResponseCatalog> of(Iterable<CatalogEntity> catalogs) {
        List<ResponseCatalog> responseCatalogs = new ArrayList<>();
        catalogs.forEach(c -> responseCatalogs.add(ResponseCatalog.of(c)));
        return responseCatalogs;
    }
}
