package com.ecommerce.productsmicroservice.dto

data class ProductVendorDTO(
    val name: String,
    val description: String?,
)

data class PatchProductVendorDTO(
    val name: String?,
    val description: String?,
)