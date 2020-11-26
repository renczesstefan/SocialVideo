package com.social.socialvideo.viewModels;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.social.socialvideo.rest.entities.LoginRequest
import com.social.socialvideo.rest.entities.LoginResponse
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.rest.services.RestApiService
import com.social.socialvideo.rest.services.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var _onRegistrationClicked = MutableLiveData<Boolean>()
    val onRegistrationClicked: LiveData<Boolean>
        get() = _onRegistrationClicked
    var _onUserLogin = MutableLiveData<ServerResponse>()
    val onUserLogin: LiveData<ServerResponse>
        get() = _onUserLogin
    var _userToken = MutableLiveData<String>()
    val userToken: LiveData<String>
        get() = _userToken

    fun onLogin() {
        val loginRequest = initLoginRequest()
        val apiService = retrofit.create(RestApiService::class.java)
        val loginResponse: Call<LoginResponse> = apiService.login(loginRequest)

        loginResponse.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                _onUserLogin.value = ServerResponse.SERVER_ERROR
            }
            override fun onResponse(
                call: Call<LoginResponse>?,
                response: Response<LoginResponse>?
            ) {
                if(response?.code() == 200) {
                    _userToken.value = response?.body()?.token
                    _onUserLogin.value = ServerResponse.SERVER_SUCCESS
                }else{
                    _onUserLogin.value = ServerResponse.SERVER_ERROR
                }
            }
        })
    }

    fun loggedIn(){
        _onUserLogin.value = ServerResponse.DEFAULT
    }
    
    fun onRegistrationClick(){
        _onRegistrationClicked.value = true
    }

    fun registrationClicked(){
        _onRegistrationClicked.value = false
    }

    private fun initLoginRequest(): LoginRequest {
        val loginRequest = LoginRequest()
        loginRequest.username = username.value.toString()
        loginRequest.password = password.value.toString()
        return loginRequest
    }

}
