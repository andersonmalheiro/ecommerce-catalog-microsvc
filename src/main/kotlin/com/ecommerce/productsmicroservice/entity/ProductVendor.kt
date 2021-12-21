package com.ecommerce.productsmicroservice.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("product_vendors")
data class ProductVendor(
    @Id
    val id: Long? = null,

    var name: String,

    var description: String?,

    @CreatedDate
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @CreatedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now(),
)
