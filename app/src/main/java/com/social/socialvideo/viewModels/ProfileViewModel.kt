package com.social.socialvideo.viewModels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.*
import com.google.gson.Gson
import com.social.socialvideo.entities.AddProfileRequest
import com.social.socialvideo.entities.UploadResponse
import com.social.socialvideo.entities.UserInfoRequest
import com.social.socialvideo.entities.UserInfoResponse
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import java.net.URL


class ProfileViewModel(private val token: String) : ViewModel() {
    var _onLogout = MutableLiveData<Boolean>()
    val onLogout: LiveData<Boolean>
        get() = _onLogout
    var _onPasswordChange = MutableLiveData<Boolean>()
    val onPasswordChange: LiveData<Boolean>
        get() = _onPasswordChange
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
    var _url = MutableLiveData<String>()
    val url : LiveData<String>
        get() = _url

    // Sluzi na zabezpecenie poslania application contextu z fragmentu do viewModelu
    // application potrebujeme na ziskanie repozitaru ktorz pracuje s databazou
    class Factory(val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(token) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    init{
        viewModelScope.launch {
            resolveProfilePicture(token)
        }
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
        val imageRequest = RequestBody.create(MediaType.parse("image/jpeg"), rawFile)
        val image = MultipartBody.Part.createFormData("image", rawFile.name, imageRequest)

        val apiService = retrofit.create(RestApiService::class.java)
        val loginResponse: Call<UploadResponse> = apiService.uploadProfilePic(image, data)

        loginResponse.enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                _uploadStatus.value = ServerResponse.SERVER_ERROR
            }
            override fun onResponse(
                call: Call<UploadResponse>?,
                response: Response<UploadResponse>?
            ) {
                if(response?.code() == 200){
                    _uploadStatus.value = ServerResponse.SERVER_SUCCESS
                }else{
                    _uploadStatus.value = ServerResponse.SERVER_ERROR
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
                    _url.value = "http://api.mcomputing.eu/mobv/uploads/" + _userInfo.value?.profile
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