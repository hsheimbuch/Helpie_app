package com.beetzung.helpie.data.model.implementation

import com.beetzung.helpie.data.LocationService

class LocationServiceDebug: LocationService {
    override suspend fun getCountryCode(): String {
        return "PL"
    }
}