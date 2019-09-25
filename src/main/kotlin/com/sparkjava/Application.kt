package com.sparkjava

import com.sparkjava.shell.manageShell
import spark.kotlin.before
import spark.kotlin.get
import spark.kotlin.port

fun main() {
    port(8080)

    before(BasicAuthFilter("admin", "admin"))

    get("/") {
        "Rest Based Shell"
    }
    manageShell()
}