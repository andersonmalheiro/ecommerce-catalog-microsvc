package com.ecommerce.productsmicroservice.handler.product_vendor

import com.ecommerce.productsmicroservice.service.ProductVendorService
import io.swagger.v3.oas.annotations.Operation
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ProductVendorAPI {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/v1/product_vendor",
                beanClass = ProductVendorService::class,
                beanMethod = "findAll",
                method = [RequestMethod.GET],
                produces = [MediaType.APPLICATION_JSON_VALUE],
                operation = Operation(
                    description = "Return all product vendors",
                )
            ),
            RouterOperation(
                path = "/api/v1/product_vendor/search",
                beanClass = ProductVendorService::class,
                beanMethod = "search",
                method = [RequestMethod.GET],
                produces = [MediaType.APPLICATION_JSON_VALUE],
                operation = Operation(
                    description = "Search product vendors",
                )
            ),
            RouterOperation(
                path = "/api/v1/product_vendor/{id}",
                beanClass = ProductVendorService::class,
                beanMethod = "findById",
                method = [RequestMethod.GET],
                produces = [MediaType.APPLICATION_JSON_VALUE]
            ),
            RouterOperation(
                path = "/api/v1/product_vendor",
                beanClass = ProductVendorService::class,
                beanMethod = "create",
                method = [RequestMethod.POST],
            ),
            RouterOperation(
                path = "/api/v1/product_vendor/{id}",
                beanClass = ProductVendorService::class,
                beanMethod = "update",
                method = [RequestMethod.PATCH]
            ),
            RouterOperation(
                path = "/api/v1/product_vendor/{id}",
                beanClass = ProductVendorService::class,
                beanMethod = "delete",
                method = [RequestMethod.DELETE]
            ),
        ],
    )
    fun productVendorRouter(handler: ProductVendorHandler) = coRouter {
        "/api/v1/product_vendor"
            .and(accept(MediaType.APPLICATION_JSON))
            .nest {
                GET("", handler::getAll)
                GET("/search", handler::search)
                GET("/{id}", handler::getById)
                POST("", handler::create)
                PATCH("/{id}", handler::update)
                DELETE("/{id}", handler::delete)
            }
    }
}