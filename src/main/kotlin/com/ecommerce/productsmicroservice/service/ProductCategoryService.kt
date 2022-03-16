package com.ecommerce.productsmicroservice.service

import com.ecommerce.productsmicroservice.dto.ProductCategoryDTO
import com.ecommerce.productsmicroservice.entity.ProductCategory
import com.ecommerce.productsmicroservice.repository.ProductCategoryRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductCategoryService(private val repository: ProductCategoryRepository) {
    suspend fun findAll(): Flux<ProductCategory> =
        repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    
    suspend fun create(resource: ProductCategoryDTO.Create): Mono<ProductCategory> =
        repository.save(
            ProductCategory(
                name = resource.name,
                description = resource.description
            )
        )

    suspend fun findById(id: Long): Mono<ProductCategory> {
        val savedResource = repository.findById(id).awaitSingleOrNull()

        return when (savedResource == null) {
            true -> Mono.error(NoSuchElementException("Resource not found"))
            false -> Mono.just(savedResource)
        }
    }

    suspend fun update(
        id: Long,
        resource: ProductCategoryDTO.Patch
    ): Mono<ProductCategory> {
        val existingProductCategory =
            repository.findById(id).awaitSingleOrNull()

        return when (existingProductCategory == null) {
            true -> Mono.error(NoSuchElementException("Resource not found"))
            false -> repository.save(
                existingProductCategory.copy(
                    description = resource.description
                        ?: existingProductCategory.description,
                    name = resource.name ?: existingProductCategory.name
                )
            )
        }
    }

    suspend fun delete(id: Long): Mono<Void> = repository.deleteById(id)
}