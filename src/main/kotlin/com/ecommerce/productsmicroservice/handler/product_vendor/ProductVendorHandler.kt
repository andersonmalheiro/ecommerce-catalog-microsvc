package com.ecommerce.productsmicroservice.handler.product_vendor

import com.ecommerce.productsmicroservice.dto.ErrorDTO
import com.ecommerce.productsmicroservice.dto.ListResponseDTO
import com.ecommerce.productsmicroservice.dto.PatchProductVendorDTO
import com.ecommerce.productsmicroservice.dto.ProductVendorDTO
import com.ecommerce.productsmicroservice.service.ProductVendorService
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ProductVendorHandler(private val service: ProductVendorService) {
    suspend fun getAll(req: ServerRequest): ServerResponse {
        val response = service.findAll().collectList().awaitSingle()

        return ServerResponse.ok().bodyValueAndAwait(ListResponseDTO(data = response))
    }

    suspend fun search(req: ServerRequest): ServerResponse {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        val nameParam: Optional<String> = req.queryParam("name")
        val createdAtGTEParam: Optional<String> = req.queryParam("createdAtGTE")
        val createdAtLTEParam: Optional<String> = req.queryParam("createdAtLTE")
        val updatedAtGTEParam: Optional<String> = req.queryParam("updatedAtGTE")
        val updatedAtLTEParam: Optional<String> = req.queryParam("updatedAtLTE")

        if ((createdAtGTEParam.isPresent && !createdAtLTEParam.isPresent) || (!createdAtGTEParam.isPresent && createdAtLTEParam.isPresent)) {
            return ServerResponse.badRequest().bodyValueAndAwait(ErrorDTO(message = "Invalid date range for createdAt"))
        }

        if ((updatedAtGTEParam.isPresent && !updatedAtLTEParam.isPresent) || (!updatedAtGTEParam.isPresent && updatedAtLTEParam.isPresent)) {
            return ServerResponse.badRequest().bodyValueAndAwait(ErrorDTO(message = "Invalid date range for updatedAt"))
        }

        val name = if (nameParam.isPresent) nameParam.get().lowercase() else null
        val createdAtGTE = if (createdAtGTEParam.isPresent) LocalDateTime.parse(createdAtGTEParam.get(), formatter) else null
        val createdAtLTE = if (createdAtLTEParam.isPresent) LocalDateTime.parse(createdAtLTEParam.get(), formatter) else null
        val updatedAtGTE = if (updatedAtGTEParam.isPresent) LocalDateTime.parse(updatedAtGTEParam.get(), formatter) else null
        val updatedAtLTE = if (updatedAtLTEParam.isPresent) LocalDateTime.parse(updatedAtLTEParam.get(), formatter) else null

        val response = service.search(name, createdAtGTE, createdAtLTE, updatedAtGTE, updatedAtLTE).collectList().awaitSingle()

        return ServerResponse.ok().bodyValueAndAwait(ListResponseDTO(data = response))
    }

    suspend fun getById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLongOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        val resource = service.findById(id).awaitSingleOrNull()
            ?: ServerResponse.notFound().buildAndAwait()

        return ServerResponse.ok().bodyValueAndAwait(resource)
    }

    suspend fun create(req: ServerRequest): ServerResponse {
        val payload = req.bodyToMono<ProductVendorDTO>().awaitSingleOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        val createdResource = service.create(payload).awaitSingle()

        return ServerResponse
            .created(URI("/api/v1/product_vendor/${createdResource.id}"))
            .bodyValueAndAwait(createdResource)
    }

    suspend fun update(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLongOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        val payload = req.bodyToMono<PatchProductVendorDTO>()
            .awaitSingleOrNull()
            ?: return ServerResponse.badRequest().buildAndAwait()

        val updatedResource = service.update(id, payload).awaitSingleOrNull()

        return when (updatedResource == null) {
            true -> ServerResponse.badRequest().buildAndAwait()
            false -> ServerResponse.noContent().buildAndAwait()
        }
    }

    suspend fun delete(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLongOrNull()
            ?: return ServerResponse.unprocessableEntity().buildAndAwait()

        service.delete(id).awaitSingleOrNull()

        return ServerResponse.ok().buildAndAwait()
    }
}