package com.ecommerce.productsmicroservice.handler.product_vendor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ProductVendorAPI {

    @Bean
    fun productVendorRouter(handler: ProductVendorHandler) = coRouter {
        "/api/v1/product_vendor"
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