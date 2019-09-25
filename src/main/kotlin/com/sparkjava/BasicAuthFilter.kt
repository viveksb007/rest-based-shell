package com.sparkjava

import spark.Filter
import spark.Request
import spark.Response
import spark.kotlin.halt
import java.util.*

class BasicAuthFilter(private val username: String, private val password: String) : Filter {

    override fun handle(request: Request, response: Response) {
        if (!request.headers().contains("Authorization") || !authenticated(request)) {
            response.header("WWW-Authenticate", BASIC_AUTHENTICATION_TYPE)
            halt(401, "Not Authenticate")
        }
    }

    private fun authenticated(request: Request): Boolean {
        val encodedHeader = request.headers("Authorization").substringAfter("Basic").trim()
        val submittedCredentials = extractCredentials(encodedHeader)
        if (submittedCredentials != null && submittedCredentials.size == NUMBER_OF_AUTHENTICATION_FIELDS) {
            val submittedUsername = submittedCredentials[0]
            val submittedPassword = submittedCredentials[1]
            return username == submittedUsername && password == submittedPassword
        }
        return false
    }

    private fun extractCredentials(encodedHeader: String?): Array<String>? {
        return if (encodedHeader != null) {
            val decodedHeader = String(Base64.getDecoder().decode(encodedHeader))
            decodedHeader.split(":").toTypedArray()
        } else {
            null
        }
    }

    companion object {
        private const val BASIC_AUTHENTICATION_TYPE = "Basic"
        private const val NUMBER_OF_AUTHENTICATION_FIELDS = 2
    }

}