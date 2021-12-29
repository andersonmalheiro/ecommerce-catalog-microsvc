package com.ecommerce.productsmicroservice.utils

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate

data class DatabaseHelper(val connectionFactory: PostgresqlConnectionFactory, val template: R2dbcEntityTemplate)

/**
 * Function to get a connection factory and a template from Testcontainers database properties
 */
fun getDBConnectionAndTemplate(
    host: String,
    port: Int,
    dbUser: String,
    dbPass: String,
    dbName: String
): DatabaseHelper {
    val connectionFactory = PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
            .host(host)
            .port(port)
            .username(dbUser)
            .password(dbPass)
            .database(dbName)
            .build()
    )

    val template = R2dbcEntityTemplate(connectionFactory)

    return DatabaseHelper(connectionFactory, template)
}