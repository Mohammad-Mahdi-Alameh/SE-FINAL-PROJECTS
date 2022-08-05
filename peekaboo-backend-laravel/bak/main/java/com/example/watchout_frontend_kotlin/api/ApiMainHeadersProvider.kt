package com.example.watchout_frontend_kotlin.api

class ApiMainHeadersProvider {

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val AUTHORIZATION = "Authorization"
        private const val HEADER_ACCEPT = "Accept"

        private fun getBearer(jwtToken: String) = "Bearer $jwtToken"

        //Public headers for calls that do not need an authenticated user.
        fun getPublicHeaders(): PublicHeaders =
            PublicHeaders().apply {
                putAll(getDefaultHeaders())
            }

        //Returns both the default headers and the headers that are mandatory for authenticated users.
        fun getAuthenticatedHeaders(jwtToken: String): AuthenticatedHeaders =
            AuthenticatedHeaders().apply {
                putAll(getDefaultHeaders())
                put(HEADER_ACCEPT, "application/json")
                put(AUTHORIZATION, getBearer(jwtToken))
            }

        //Default headers used on all calls.
        private fun getDefaultHeaders() = mapOf(
            CONTENT_TYPE to "application/json",
        )

    }
}

open class ApiMainHeaders : HashMap<String, String>()
class AuthenticatedHeaders : ApiMainHeaders()
class PublicHeaders : ApiMainHeaders()