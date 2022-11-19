package com.beetzung.helpie.data

interface LocationService {
    suspend fun getCountryCode(): String
}