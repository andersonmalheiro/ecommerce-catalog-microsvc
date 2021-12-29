package com.ecommerce.productsmicroservice.utils

import org.testcontainers.containers.PostgreSQLContainer

class KPostgreSQLContainer(image: String) : PostgreSQLContainer<KPostgreSQLContainer>(image)

class PostgresTestContainerBuilder {

    companion object {
        private var dbIpAddr = "127.0.0.1"
        private var dbName = "test_db"
        private var dbUser = "test_user"
        private var dbPass = "qwe123"
        private var dbHost = "localhost"
        private var dbPort = 5432
        private var dbInitScript = "db/database_init.sql"

        fun withHostName(host: String) {
            dbHost = host
        }

        fun withIpAddress(ip: String) {
            dbIpAddr = ip
        }

        fun withPort(port: Int) {
            dbPort = port
        }

        fun withUser(user: String) {
            dbUser = user
        }

        fun withPassword(password: String) {
            dbPass = password
        }

        fun withName(name: String) {
            dbName = name
        }

        fun withCustomInitScript(script: String) {
            dbInitScript = script
        }

        fun build(): KPostgreSQLContainer {
            return KPostgreSQLContainer("postgres:12").apply {
                withDatabaseName(dbName)
                withUsername(dbUser)
                withPassword(dbPass)
                withExtraHost(dbHost, dbIpAddr)
                withInitScript(dbInitScript)
                withExposedPorts(dbPort).apply {
                    start()
                }
            }
        }
    }
}
