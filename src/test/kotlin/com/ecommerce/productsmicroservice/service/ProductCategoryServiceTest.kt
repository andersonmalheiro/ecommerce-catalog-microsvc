package com.ecommerce.productsmicroservice.service

import com.ecommerce.productsmicroservice.dto.ProductCategoryDTO
import com.ecommerce.productsmicroservice.entity.ProductCategory
import com.ecommerce.productsmicroservice.repository.ProductCategoryRepository
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
internal class ProductCategoryServiceTest {
    @Mock
    lateinit var repository: ProductCategoryRepository

    lateinit var sut: ProductCategoryService

    @BeforeAll
    fun setUp() {
        sut = ProductCategoryService(repository)
    }

    @Nested
    @DisplayName("create()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Create {
        @Test
        fun `should create a product category`() = runTest {
            // given
            val resource = ProductCategoryDTO.Create(
                name = "p_category",
                description = "p_desc"
            )
            val result = ProductCategory(
                name = resource.name,
                description = resource.description
            )

            // when
            Mockito.`when`(repository.save(ArgumentMatchers.any(ProductCategory::class.java)))
                .thenReturn(Mono.just(result))

            // then
            val source = sut.create(resource)
            StepVerifier
                .create(source)
                .expectNextMatches { it.name === resource.name }
                .verifyComplete()
        }
    }

    @Nested
    @DisplayName("update()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Update {
        @Test
        fun `should update a product category`() = runTest {
            // given
            val savedResource = ProductCategory(
                id = 1,
                name = "electronics",
                description = null
            )
            val resourceId = 1L
            val resource = ProductCategoryDTO.Patch(
                name = "hardware",
                description = "hardware things"
            )

            // when
            Mockito.`when`(repository.findById(ArgumentMatchers.any(Long::class.java)))
                .thenReturn(Mono.just(savedResource))

            Mockito.`when`(repository.save(ArgumentMatchers.any(ProductCategory::class.java)))
                .thenReturn(
                    Mono.just(
                        ProductCategory(
                            name = resource.name!!,
                            description = resource.description
                        )
                    )
                )

            // then
            val source = sut.update(id = resourceId, resource = resource)
            StepVerifier
                .create(source)
                .expectNextMatches {
                    it.name === resource.name
                    it.description === resource.description
                }
                .verifyComplete()
        }

        @Test
        fun `should throw error when id in not found`() = runTest {
            // given
            val resourceId = 999L
            val resource = ProductCategoryDTO.Patch(
                name = "hardware",
                description = "hardware things"
            )

            // when
            Mockito.`when`(repository.findById(ArgumentMatchers.eq(resourceId)))
                .thenReturn(Mono.empty())

            // then
            val source = sut.update(id = resourceId, resource = resource)
            StepVerifier
                .create(source)
                .expectErrorMatches {
                    it is NoSuchElementException && it.message.equals("Resource not found")
                }
                .verify()
        }
    }

    @Nested
    @DisplayName("findById()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindById {
        @Test
        fun `should return the given product category`() = runTest {
            // given
            val resourceId = 1L
            val savedResource = ProductCategory(
                id = 1L,
                name = "electronics",
                description = null
            )

            // when
            Mockito.`when`(repository.findById(ArgumentMatchers.eq(resourceId)))
                .thenReturn(Mono.just(savedResource))

            // then
            val source = sut.findById(resourceId)
            StepVerifier
                .create(source)
                .expectNextMatches {
                    it.name == savedResource.name &&
                            it.description.equals(savedResource.description) &&
                            it.id == savedResource.id
                }
                .verifyComplete()
        }

        @Test
        fun `should return error when given id is not found`() = runTest {
            // given
            val resourceId = 999L

            // when
            Mockito.`when`(repository.findById(ArgumentMatchers.eq(resourceId)))
                .thenReturn(Mono.empty())

            // then
            val source = sut.findById(resourceId)
            StepVerifier
                .create(source)
                .expectErrorMatches {
                    it is NoSuchElementException && it.message.equals("Resource not found")
                }
                .verify()
        }
    }

    @Nested
    @DisplayName("delete()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Delete {
        @Test
        fun `should delete the given resource`() = runTest {
            // given
            val resourceId = 1L

            // when
            Mockito.`when`(repository.deleteById(ArgumentMatchers.eq(resourceId)))
                .thenReturn(Mono.empty())

            // then
            val source = sut.delete(resourceId)
            StepVerifier
                .create(source)
                .expectNextCount(0)
                .verifyComplete()

            Mockito.verify(repository).deleteById(resourceId)
        }
    }
}