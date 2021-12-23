package com.ecommerce.productsmicroservice

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	info = Info(title = "Product Microservice API", version = "1.0", description = "Documentation APIs v1.0")
)
@SpringBootApplication
class ProductsMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<ProductsMicroserviceApplication>(*args)
}
