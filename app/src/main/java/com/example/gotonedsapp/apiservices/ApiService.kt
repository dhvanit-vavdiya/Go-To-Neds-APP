package com.example.gotonedsapp.apiservices

import com.example.gotonedsapp.utils.Constants
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(Constants.HEADER)
    @GET(Constants.END_POINT)
    fun getData(@Query(Constants.API_METHOD) method: String,
                @Query(Constants.API_COUNT) count: Int): Call<JsonObject>

    companion object {
        private var apiService: ApiService? = null

        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }

    }
}