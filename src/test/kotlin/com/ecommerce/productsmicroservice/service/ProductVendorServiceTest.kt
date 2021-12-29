package com.ecommerce.productsmicroservice.service

import com.ecommerce.productsmicroservice.dto.ProductVendorDTO
import com.ecommerce.productsmicroservice.entity.ProductVendor
import com.ecommerce.productsmicroservice.repository.ProductVendorRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


@ExperimentalCoroutinesApi
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductVendorServiceTest {
    @Mock
    lateinit var repository: ProductVendorRepository

    // sut means `system under test`
    lateinit var sut: ProductVendorService

    @BeforeAll
    fun setUp() {
        sut = ProductVendorService(repository)
    }

    @Nested
    @DisplayName("create()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Create {

        @Test
        fun `should create a product vendor`() = runTest {
            // given
            val resource = ProductVendorDTO(name = "p_name", description = "p_desc")
            val result = ProductVendor(name = resource.name, description = resource.description)

            // when
            Mockito.`when`(repository.save(ArgumentMatchers.any(ProductVendor::class.java)))
                .thenReturn(Mono.just(result))

            // then
            StepVerifier
                .create(sut.create(resource))
                .expectNextMatches { it.name === resource.name }
                .verifyComplete()
        }
    }
}