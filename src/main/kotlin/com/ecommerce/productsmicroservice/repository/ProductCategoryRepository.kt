package com.ecommerce.productsmicroservice.repository

import com.ecommerce.productsmicroservice.entity.ProductCategory
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductCategoryRepository : R2dbcRepository<ProductCategory, Long>