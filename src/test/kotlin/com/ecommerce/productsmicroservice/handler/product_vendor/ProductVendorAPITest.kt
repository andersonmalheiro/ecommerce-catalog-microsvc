package com.ecommerce.productsmicroservice.handler.product_vendor

import com.ecommerce.productsmicroservice.entity.ProductVendor
import com.ecommerce.productsmicroservice.repository.ProductVendorRepository
import com.ecommerce.productsmicroservice.service.ProductVendorService
import com.ecommerce.productsmicroservice.utils.PostgresTestContainerBuilder
import com.ecommerce.productsmicroservice.utils.getDBConnectionAndTemplate
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier


@ComponentScan("com.ecommerce.productsmicroservice")
@DataR2dbcTest
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
internal class ProductVendorAPITest @Autowired constructor(private val repository: ProductVendorRepository) {

    private val baseUrl = "/api/v1/product_vendor"

    private val service = ProductVendorService(repository)

    private val handler = ProductVendorHandler(service)

    private val api = ProductVendorAPI()

    private val client = WebTestClient
        .bindToRouterFunction(api.productVendorRouter(handler)).build()

    private lateinit var connectionFactory: PostgresqlConnectionFactory

    private lateinit var template: R2dbcEntityTemplate

    companion object {
        @Container
        private val pgContainer = PostgresTestContainerBuilder.build()

        val host: String = pgContainer.host
        val port: Int = pgContainer.firstMappedPort
        val dbUser: String = pgContainer.username
        val dbPass: String = pgContainer.password
        val dbName: String = pgContainer.databaseName

        /**
         * Setting R2DBC properties temporally from Testcontainers database
         */
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            val path = "postgresql://$host:$port/$dbName"

            registry.add("spring.r2dbc.url") { "r2dbc:$path" }
            registry.add("spring.r2dbc.username") { dbUser }
            registry.add("spring.r2dbc.password") { dbPass }
            registry.add("spring.r2dbc.name") { dbName }
        }
    }

    @BeforeAll
    fun setUp() {
        /*
         * Getting database connection factory and R2DBC template to use on db operations
         */
        val dbConfig = getDBConnectionAndTemplate(host, port, dbUser, dbPass, dbName)
        connectionFactory = dbConfig.connectionFactory
        template = dbConfig.template
    }

    @AfterEach
    fun clear() = runBlocking {
        println("-> Clearing database...")
        repository.deleteAll()
        println("-> Database cleared.")
    }

    @Nested
    @DisplayName("get all")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAll {
        @Test
        fun `should return all vendors`() = runTest {
            // given
            val vendors = listOf(
                ProductVendor(name = "vendor_1", description = "vendor_1_desc"),
                ProductVendor(name = "vendor_2", description = "vendor_2_desc"),
                ProductVendor(name = "vendor_3", description = "vendor_3_desc"),
            )

            vendors.forEach { vendor ->
                template.insert(ProductVendor::class.java)
                    .using(vendor)
                    .doOnNext { println(it) }
                    .`as`(StepVerifier::create)
                    .assertNext { t -> Assertions.assertEquals(t.name, vendor.name) }
                    .verifyComplete()
            }

            // when/then
            client.get()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data.length()")
                .isEqualTo(3)
                .jsonPath("$.data[0].name")
                .isEqualTo("vendor_1")
        }
    }
}