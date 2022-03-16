package com.ecommerce.productsmicroservice.dto


class ProductVendorDTO {
    data class Create(
        val name: String,
        val description: String?,
    )

    data class Patch(
        val name: String?,
        val description: String?,
    )
}
