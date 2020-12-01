package com.social.socialvideo.viewModels

import androidx.lifecycle.*
import com.google.gson.Gson
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.rest.entities.*
import com.social.socialvideo.rest.services.RestApiService
import com.social.socialvideo.rest.services.retrofit
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileViewModel(private val token: String) : ViewModel() {

    var _onUserPostsNavigate = MutableLiveData<Boolean>()
    val onUserPostsNavigate: LiveData<Boolean>
        get() = _onUserPostsNavigate
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
    var _deletePic = MutableLiveData<Boolean>()
    val deletePic : LiveData<Boolean>
        get() = _deletePic

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

    init {
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

    fun deleteProfilePicture(){
        _deletePic.value = true
    }

    fun deleteProfilePic(token: String) {
        val requestData = DeletePhotoRequest()
        requestData.token = token
        val apiService = retrofit.create(
            RestApiService::class.java)
        val uploadResponse: Call<UploadResponse> = apiService.deletePhoto(requestData)

        uploadResponse.enqueue(object : Callback<UploadResponse> {

            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                _uploadStatus.value = ServerResponse.SERVER_ERROR
            }

            override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                if (response?.code() == 200) {
                    _uploadStatus.value = ServerResponse.SERVER_SUCCESS
                    _url.value = ""
                } else {
                    _uploadStatus.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

    fun resetState() {
        _addImage.value = false
        _uploadStatus.value = ServerResponse.DEFAULT
        _deletePic.value = false
    }

    fun onUserPostsNavigate() {
        _onUserPostsNavigate.value = true
    }

    fun resetUserPostsNavigate() {
        _onUserPostsNavigate.value = false
    }

    fun uploadProfilePic(path: String, token: String){
        val rawFile = File(path)
        val rawData = UploadFileRequest()
        rawData.token = token

        val data = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(rawData))
        val imageRequest = RequestBody.create(MediaType.parse("image/jpeg"), rawFile)
        val image = MultipartBody.Part.createFormData("image", rawFile.name, imageRequest)

        val apiService = retrofit.create(
            RestApiService::class.java)
        val uploadResponse: Call<UploadResponse> = apiService.uploadProfilePic(image, data)

        uploadResponse.enqueue(object : Callback<UploadResponse> {

            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                _uploadStatus.value = ServerResponse.SERVER_ERROR
            }

            override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                if (response?.code() == 200) {
                    _uploadStatus.value = ServerResponse.SERVER_SUCCESS
                    resolveProfilePicture(token)
                } else {
                    _uploadStatus.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

    fun resolveProfilePicture(token: String) {
        val userInfoRequest = initUserInfoRequest(token)
        val apiService = retrofit.create(
            RestApiService::class.java)
        val userInfoResponse: Call<UserInfoResponse> = apiService.userInfo(userInfoRequest)

        userInfoResponse.enqueue(object : Callback<UserInfoResponse> {

            override fun onFailure(call: Call<UserInfoResponse>?, t: Throwable?) {
                _userInfoStatus.value = ServerResponse.SERVER_ERROR
            }

            override fun onResponse(call: Call<UserInfoResponse>?, response: Response<UserInfoResponse>?) {
                if (response?.code() == 200) {
                    _userInfo.value = response.body()
                    _url.value = "http://api.mcomputing.eu/mobv/uploads/" + _userInfo.value?.profile
                    _userInfoStatus.value = ServerResponse.SERVER_SUCCESS

                } else {
                    _userInfoStatus.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

    private fun initUserInfoRequest(token: String): UserInfoRequest {
        val userInfoRequest =
            UserInfoRequest()
        userInfoRequest.token = token
        return userInfoRequest
    }
}