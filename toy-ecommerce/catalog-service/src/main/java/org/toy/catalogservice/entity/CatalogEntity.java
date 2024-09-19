package org.toy.catalogservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catalog")
public class CatalogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Setter
    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer unitPrice;

    @CreatedDate
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP(6)")
    private LocalDateTime createdAt;
}
