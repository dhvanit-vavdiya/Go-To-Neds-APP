package com.example.gotonedsapp.utils

class Constants {

    companion object {

        //API data
        const val BASE_URL = "https://api.neds.com.au/rest/v1/"
        const val END_POINT = "racing/?"
        const val HEADER = "Content-type: application/json"

        //Race parameters to get data from API
        const val API_METHOD = "method"
        const val API_COUNT = "count"
        const val RACE_METHOD = "nextraces"
        const val COUNT = 10

        //Category of Race
        const val GREYHOUND = "9daef0d7-bf3c-4f50-921d-8e818c60fe61"
        const val HARNESS = "161d9be2-e909-4326-8c2c-35ed71fb460b"
        const val HORSE = "4a2788f8-e825-4d36-9894-efd4baf1cfae"

        const val greyHound = "Greyhound"
        const val harness = "Harness"
        const val horse = "Horse"

    }
}