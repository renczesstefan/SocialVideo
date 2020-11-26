package com.social.socialvideo.rest.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.social.socialvideo.rest.entities.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part



private const val BASE_URL = "http://api.mcomputing.eu/mobv/"

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()



interface RestApiService {

    @POST("service.php")
    fun createUser(@Body registration: RegistrationRequest): Call<RegistrationResponse>

    @POST("service.php")
    fun login(@Body login: LoginRequest): Call<LoginResponse>

    @POST("service.php")
    fun changePassword(@Body changePassword: ChangePasswordRequest): Call<ChangePasswordResponse>

    @POST("service.php")
    fun checkUsername(@Body checkUsername: CheckUsernameRequest): Call<CheckUsernameResponse>

    @POST("service.php")
    fun userInfo(@Body userInfo: UserInfoRequest): Call<UserInfoResponse>

    @POST("service.php")
    fun userPosts(@Body userInfo: UserPostsRequest): Deferred<List<UserPostsResponse>>

    @Multipart
    @POST("upload.php")
    fun uploadProfilePic(@Part image: MultipartBody.Part, @Part("data") data: RequestBody): Call<UploadResponse>

    @Multipart
    @POST("post.php")
    fun uploadVideo(@Part video: MultipartBody.Part, @Part("data") data: RequestBody): Call<UploadResponse>

}





