package com.example.watchout_frontend_kotlin.api

class ApiMainHeadersProvider {

    companion object {
        private const val CONTENT_TYPE = "Content-Type"

        //Default headers used on all calls.
        private fun getDefaultHeaders() = mapOf(
            CONTENT_TYPE to "application/json",
        )

    }
}

open class ApiMainHeaders : HashMap<String, String>()
class AuthenticatedHeaders : ApiMainHeaders()
class PublicHeaders : ApiMainHeaders()