package com.social.socialvideo.viewModels

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.social.socialvideo.entities.AddProfileRequest
import com.social.socialvideo.entities.UploadResponse
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
                _uploadStatus.value = ServerResponse.SERVER_ERROR
            }
            override fun onResponse(
                call: Call<UploadResponse>?,
                response: Response<UploadResponse>?
            ) {
                if(response?.code() == 200){
                    _uploadStatus.value = ServerResponse.SERVER_SUCCESS
                } else {
                    _uploadStatus.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

}