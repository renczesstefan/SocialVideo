package com.social.socialvideo.viewModels

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.social.socialvideo.entities.*
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.retrofit
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileViewModel : ViewModel() {
    var _onLogout = MutableLiveData<Boolean>()
    val onLogout: LiveData<Boolean>
        get() = _onLogout
    var _onPasswordChange = MutableLiveData<Boolean>()
    val onPasswordChange: LiveData<Boolean>
        get() = _onPasswordChange
    var _imageView = MutableLiveData<ImageView>()
    val imageView: LiveData<ImageView>
        get() = _imageView
    var _addImage = MutableLiveData<Boolean>()
    val addImage: LiveData<Boolean>
        get() = _addImage
    var _uploadStatus = MutableLiveData<ServerResponse>()
    val uploadStatus: LiveData<ServerResponse>
        get() = _uploadStatus
    var _userInfo = MutableLiveData<UserInfoResponse>()
    val userInfo : LiveData<UserInfoResponse>
        get() = _userInfo
    var _userInfoStatus = MutableLiveData<ServerResponse>()
    val userInfoStatus : LiveData<ServerResponse>
        get() = _userInfoStatus


    init{

    }

    fun onLogout() {
        _onLogout.value = true
    }

    fun onPasswordChange() {
        _onPasswordChange.value = true
    }

    fun resetOnLogout() {
        _onLogout.value = false
    }

    fun resetOnPasswordChange() {
        _onPasswordChange.value = false
    }

    fun addProfilePicture() {
        _addImage.value = true
    }

    fun resetState(){
        _addImage.value = false
        _uploadStatus.value = ServerResponse.DEFAULT
    }

    fun uploadProfilePic(path: String, token: String){
        val rawFile = File(path)
        val rawData = AddProfileRequest()
        rawData.token = token

        val data = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(rawData))
        val imageRequest = RequestBody.create(MediaType.parse("image/jpeg"), rawFile);
        val image = MultipartBody.Part.createFormData("image", rawFile.name, imageRequest)

        val apiService = retrofit.create(RestApiService::class.java)
        val loginResponse: Call<UploadResponse> = apiService.uploadProfilePic(image, data)

        loginResponse.enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {

            }
            override fun onResponse(
                call: Call<UploadResponse>?,
                response: Response<UploadResponse>?
            ) {
                if(response?.code() == 200){
                    _uploadStatus.value = ServerResponse.SERVER_SUCCESS
                }
            }
        })
    }

    fun resolveProfilePicture(token: String) {
        val userInfoRequest = initUserInfoRequest(token)
        val apiService = retrofit.create(RestApiService::class.java)
        val loginResponse: Call<UserInfoResponse> = apiService.userInfo(userInfoRequest)

        loginResponse.enqueue(object : Callback<UserInfoResponse> {
            override fun onFailure(call: Call<UserInfoResponse>?, t: Throwable?) {
                _userInfoStatus.value = ServerResponse.SERVER_ERROR
            }
            override fun onResponse(
                call: Call<UserInfoResponse>?,
                response: Response<UserInfoResponse>?
            ) {
                if(response?.code() == 200) {
                    _userInfo.value = response.body()
                    _userInfoStatus.value = ServerResponse.SERVER_SUCCESS
                }else{
                    _userInfoStatus.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

    private fun initUserInfoRequest(token: String): UserInfoRequest {
        val userInfoRequest = UserInfoRequest()
        userInfoRequest.token = token
        return userInfoRequest
    }
}