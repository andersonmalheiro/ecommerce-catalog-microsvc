package com.ecommerce.productsmicroservice.repository

import com.ecommerce.productsmicroservice.entity.ProductVendor
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductVendorRepository : R2dbcRepository<ProductVendor, Long>