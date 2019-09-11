package com.sparkjava

import com.sparkjava.shell.manageShell
import spark.kotlin.get
import spark.kotlin.port

fun main() {
    port(8080)
    get("/") {
        "Rest Based Shell"
    }
    manageShell()
}