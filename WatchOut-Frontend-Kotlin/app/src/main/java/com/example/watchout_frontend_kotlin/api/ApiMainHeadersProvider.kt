package com.example.watchout_frontend_kotlin.api

class ApiMainHeadersProvider {

    companion object {

    }
}

open class ApiMainHeaders : HashMap<String, String>()
class AuthenticatedHeaders : ApiMainHeaders()
class PublicHeaders : ApiMainHeaders()