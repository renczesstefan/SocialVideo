package com.social.socialvideo.network

import com.social.socialvideo.entities.RegistrationRequest
import com.social.socialvideo.entities.RegistrationResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL = "http://api.mcomputing.eu/mobv/service.php/"



/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()



interface RestApiService {

    @POST(".")
    fun createUser(@Body registration: RegistrationRequest): Call<RegistrationResponse>

}





