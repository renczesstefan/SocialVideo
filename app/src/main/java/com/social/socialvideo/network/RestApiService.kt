package com.social.socialvideo.network

import com.social.socialvideo.entities.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val BASE_URL = "http://api.mcomputing.eu/mobv/"



/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()



interface RestApiService {

    @POST("service.php")
    fun createUser(@Body registration: RegistrationRequest): Call<RegistrationResponse>

    @POST("service.php")
    fun login(@Body login: LoginRequest): Call<LoginResponse>

    @POST("service.php")
    fun changePassword(@Body login: ChangePasswordRequest): Call<ChangePasswordResponse>

    @POST("service.php")
    fun checkUsername(@Body login: CheckUsernameRequest): Call<CheckUsernameResponse>

    @POST("service.php")
    fun userInfo(@Body login: UserInfoRequest): Call<UserInfoResponse>

    @Multipart
    @POST("upload.php")
    fun uploadProfilePic(@Part image: MultipartBody.Part, @Part("data") data: RequestBody): Call<UploadResponse>

}





