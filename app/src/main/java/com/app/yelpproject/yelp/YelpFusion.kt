package com.app.yelpproject.yelp

import com.yelp.fusion.client.connection.YelpFusionApiFactory

class YelpFusion {

    companion object {
        const val API =
            "vYyIddUHYmKKkptVBN93XRIXfR-J-EgOmgg1unZTfIRZES4V_QD-79W4bszMvbJnIL_di0fHaidBHCuJxwrouHmNVmUtSnZpfZ4ZAmTzI2luATmMU4VX4_zSp5LrXHYx"
        val instance = YelpFusionApiFactory().createAPI(API)!!

        fun createParams(): HashMap<String, String> {
            val params = HashMap<String, String>()
            params["term"] = "restaurants"
            return params
        }
    }
}