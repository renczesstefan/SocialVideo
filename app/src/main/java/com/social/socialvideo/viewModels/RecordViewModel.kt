package com.social.socialvideo.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.rest.entities.UploadFileRequest
import com.social.socialvideo.rest.entities.UploadResponse
import com.social.socialvideo.rest.services.RestApiService
import com.social.socialvideo.rest.services.retrofit
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class RecordViewModel : ViewModel() {
    var _uploadStatus = MutableLiveData<ServerResponse>()
    val uploadStatus: LiveData<ServerResponse>
        get() = _uploadStatus

    fun resetState() {
        _uploadStatus.value = ServerResponse.DEFAULT
    }

    fun upload(path:String, token:String){
        val rawFile = File(path)
        val rawData = UploadFileRequest()
        rawData.token = token

        val data = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(rawData))
        val videoRequest = RequestBody.create(MediaType.parse("video/mp4"), rawFile)
        val video = MultipartBody.Part.createFormData("video", rawFile.name, videoRequest)

        val apiService = retrofit.create(
            RestApiService::class.java)
        val uploadResponse: Call<UploadResponse> = apiService.uploadVideo(video, data)

        uploadResponse.enqueue(object : Callback<UploadResponse> {

            override fun onFailure(call: Call<UploadResponse>?, t: Throwable?) {
                _uploadStatus.value = ServerResponse.SERVER_ERROR
            }

            override fun onResponse(call: Call<UploadResponse>?, response: Response<UploadResponse>?) {
                if (response?.code() == 200) {
                    _uploadStatus.value = ServerResponse.SERVER_SUCCESS
                } else {
                    _uploadStatus.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

}