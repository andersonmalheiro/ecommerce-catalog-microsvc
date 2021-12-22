package com.ecommerce.productsmicroservice.repository

import com.ecommerce.productsmicroservice.entity.ProductVendor
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface ProductVendorRepository : R2dbcRepository<ProductVendor, Long> {
    @Query("""
        SELECT * 
        FROM product_vendors pv 
        WHERE (LOWER(pv.name) = :name OR :name IS NULL) 
            AND (pv.created_at BETWEEN :createdAtGTE AND :createdAtLTE OR (:createdAtGTE IS NULL AND :createdAtLTE IS NULL)) 
            AND (pv.updated_at BETWEEN :updatedAtGTE AND :updatedAtLTE OR (:updatedAtGTE IS NULL AND :updatedAtLTE IS NULL))
     """)
    suspend fun search(
        name: String?,
        createdAtGTE: LocalDateTime?,
        createdAtLTE: LocalDateTime?,
        updatedAtGTE: LocalDateTime?,
        updatedAtLTE: LocalDateTime?,
    ): Flux<ProductVendor>
}