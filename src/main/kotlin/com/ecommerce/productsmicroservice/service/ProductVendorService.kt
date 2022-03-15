package com.ecommerce.productsmicroservice.service

import com.ecommerce.productsmicroservice.dto.ProductVendorDTO
import com.ecommerce.productsmicroservice.entity.ProductVendor
import com.ecommerce.productsmicroservice.repository.ProductVendorRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ProductVendorService(private val repository: ProductVendorRepository) {
    suspend fun findAll(): Flux<ProductVendor> = repository.findAll(Sort.by(Sort.Direction.ASC, "id"))

    suspend fun search(
        name: String?,
        createdAtGTE: LocalDateTime?,
        createdAtLTE: LocalDateTime?,
        updatedAtGTE: LocalDateTime?,
        updatedAtLTE: LocalDateTime?,
    ): Flux<ProductVendor> = repository.search(name, createdAtGTE, createdAtLTE, updatedAtGTE, updatedAtLTE)

    suspend fun findById(id: Long): Mono<ProductVendor> = repository.findById(id)

    suspend fun create(resource: ProductVendorDTO.Create): Mono<ProductVendor> = repository.save(
        ProductVendor(
            name = resource.name, description = resource.description
        )
    )

    suspend fun update(id: Long, resource: ProductVendorDTO.Patch): Mono<ProductVendor> {
        val existingProductVendor = repository.findById(id).awaitSingleOrNull()

        return when (existingProductVendor == null) {
            true -> throw NoSuchElementException("Resource not found")
            false -> repository.save(
                existingProductVendor.copy(
                    description = resource.description ?: existingProductVendor.description,
                    name = resource.name ?: existingProductVendor.name,
                )
            )
        }
    }

    suspend fun delete(id: Long): Mono<Void> {
        return repository.deleteById(id)
    }
}