package com.social.socialvideo.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.RegistrationBinding
import com.social.socialvideo.entities.RegistrationRequest
import com.social.socialvideo.entities.RegistrationResponse
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.retrofit
import com.social.socialvideo.utils.PasswordUtil
import retrofit2.Call
import retrofit2.Callback


class RegistrationFragment : Fragment() {

        private lateinit var binding: RegistrationBinding


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
                // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.registration, container, false)
                binding.registerModel = this

                return binding.root
        }

        fun registerUser() {
                if (PasswordUtil.checkMatchingPasswords(binding.password.text.toString(), binding.passwordConfirm.text.toString(), this.context)) {

                        val registrationRequest =
                                RegistrationRequest(binding.email.text.toString(), binding.userID.text.toString(), binding.password.text.toString())
                        val apiService = retrofit.create(RestApiService::class.java)
                        val regResponse: Call<RegistrationResponse> = apiService.createUser(registrationRequest)

                        regResponse.request().body()

                        navigateBack()

                } else {
                        binding.password.text.clear()
                        binding.passwordConfirm.text.clear()
                }
        }

        fun navigateBack() {
                this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

}
