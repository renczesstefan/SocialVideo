package com.social.socialvideo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.social.socialvideo.rest.entities.ChangePasswordRequest
import com.social.socialvideo.rest.entities.ChangePasswordResponse
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.rest.services.RestApiService
import com.social.socialvideo.rest.services.retrofit
import com.social.socialvideo.utils.PasswordUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {
    var oldpassword = MutableLiveData<String>()
    var newpassword = MutableLiveData<String>()
    var newpasswordConfirm = MutableLiveData<String>()
    var _onChangeResponse = MutableLiveData<ServerResponse>()
    val onChangeResponse: LiveData<ServerResponse>
        get() = _onChangeResponse
    var token: String = ""
    var _newToken = MutableLiveData<String>()
    val newToken: LiveData<String>
        get() = _newToken
    var _newRefreshToken = MutableLiveData<String>()
    val newRefreshToken: LiveData<String>
        get() = _newRefreshToken

    fun onChange() {
        if (PasswordUtil.checkMatchingPasswords(newpassword.value, newpasswordConfirm.value)) {
            val changePasswordRequest = initChangePasswordRequest()
            val apiService = retrofit.create(
                RestApiService::class.java)
            val loginResponse: Call<ChangePasswordResponse> =
                apiService.changePassword(changePasswordRequest)

            loginResponse.enqueue(object : Callback<ChangePasswordResponse> {
                override fun onFailure(call: Call<ChangePasswordResponse>?, t: Throwable?) {
                    _onChangeResponse.value = ServerResponse.SERVER_ERROR
                }
                override fun onResponse(
                    call: Call<ChangePasswordResponse>?,
                    response: Response<ChangePasswordResponse>?
                ) {
                    if (response?.code() == 200) {
                        _newToken.value = response?.body()?.token
                        _newRefreshToken.value = response?.body()?.token
                        _onChangeResponse.value = ServerResponse.SERVER_SUCCESS
                    } else {
                        _onChangeResponse.value = ServerResponse.SERVER_ERROR
                    }
                }
            })
        } else {
            _onChangeResponse.value = ServerResponse.PASSWORD_MISMATCH
            oldpassword.value = ""
            newpassword.value = ""
            newpasswordConfirm.value = ""
        }
    }

    fun resetOnChange() {
        _onChangeResponse.value = ServerResponse.DEFAULT
    }

    private fun initChangePasswordRequest(): ChangePasswordRequest {
        val changePasswordRequest = ChangePasswordRequest()
        changePasswordRequest.token = token
        changePasswordRequest.oldpassword = oldpassword.value.toString()
        changePasswordRequest.newpassword = newpassword.value.toString()
        return changePasswordRequest
    }
}