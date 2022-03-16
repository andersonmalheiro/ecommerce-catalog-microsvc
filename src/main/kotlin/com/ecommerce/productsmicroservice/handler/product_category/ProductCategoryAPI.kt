package com.ecommerce.productsmicroservice.handler.product_category

import com.ecommerce.productsmicroservice.service.ProductCategoryService
import io.swagger.v3.oas.annotations.Operation
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ProductCategoryAPI {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/v1/product_category",
                beanClass = ProductCategoryService::class,
                beanMethod = "findAll",
                method = [RequestMethod.GET],
                produces = [MediaType.APPLICATION_JSON_VALUE],
                operation = Operation(
                    description = "Return all product categories",
                )
            ),
            RouterOperation(
                path = "/api/v1/product_category/{id}",
                beanClass = ProductCategoryService::class,
                beanMethod = "findById",
                method = [RequestMethod.GET],
                produces = [MediaType.APPLICATION_JSON_VALUE]
            ),
            RouterOperation(
                path = "/api/v1/product_category",
                beanClass = ProductCategoryService::class,
                beanMethod = "create",
                method = [RequestMethod.POST],
            ),
            RouterOperation(
                path = "/api/v1/product_category/{id}",
                beanClass = ProductCategoryService::class,
                beanMethod = "update",
                method = [RequestMethod.PATCH]
            ),
            RouterOperation(
                path = "/api/v1/product_category/{id}",
                beanClass = ProductCategoryService::class,
                beanMethod = "delete",
                method = [RequestMethod.DELETE]
            ),
        ],
    )
    fun productCategoryRouter(handler: ProductCategoryHandler) = coRouter {
        "/api/v1/product_category"
            .and(accept(MediaType.APPLICATION_JSON))
            .nest {
                GET("", handler::getAll)
                GET("/{id}", handler::getById)
                POST("", handler::create)
                PATCH("/{id}", handler::update)
                DELETE("/{id}", handler::delete)
            }
    }
}