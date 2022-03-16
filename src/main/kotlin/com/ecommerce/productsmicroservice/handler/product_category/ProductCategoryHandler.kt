package com.ecommerce.productsmicroservice.handler.product_category

import com.ecommerce.productsmicroservice.dto.ListResponseDTO
import com.ecommerce.productsmicroservice.dto.ProductCategoryDTO
import com.ecommerce.productsmicroservice.service.ProductCategoryService
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*
import java.net.URI

@Service
class ProductCategoryHandler(private val service: ProductCategoryService) {
    suspend fun getAll(req: ServerRequest): ServerResponse {
        val response = service.findAll().collectList().awaitSingle()

        return ServerResponse.ok()
            .bodyValueAndAwait(ListResponseDTO(data = response))
    }

    suspend fun create(req: ServerRequest): ServerResponse {
        val payload =
            req.bodyToMono<ProductCategoryDTO.Create>().awaitSingleOrNull()
                ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        val createdResource = service.create(payload).awaitSingle()

        return ServerResponse
            .created(URI("/api/v1/product_category/${createdResource.id}"))
            .bodyValueAndAwait(createdResource)
    }

    suspend fun update(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLongOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        val payload = req.bodyToMono<ProductCategoryDTO.Patch>()
            .awaitSingleOrNull()
            ?: return ServerResponse.badRequest().buildAndAwait()

        val updatedResource = service.update(id, payload).awaitSingleOrNull()

        return when (updatedResource == null) {
            true -> ServerResponse.badRequest().buildAndAwait()
            false -> ServerResponse.noContent().buildAndAwait()
        }
    }

    suspend fun getById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLongOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        val resource = service.findById(id).awaitSingleOrNull()
            ?: ServerResponse.notFound().buildAndAwait()

        return ServerResponse.ok().bodyValueAndAwait(resource)
    }

    suspend fun delete(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLongOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        service.delete(id).awaitSingleOrNull()

        return ServerResponse.ok().buildAndAwait()
    }
}