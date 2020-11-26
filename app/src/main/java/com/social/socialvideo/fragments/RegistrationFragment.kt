package com.social.socialvideo.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.RegistrationBinding
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.viewModels.RegistrationViewModel


class RegistrationFragment : Fragment() {

        private lateinit var binding: RegistrationBinding
        private lateinit var registrationViewModel: RegistrationViewModel


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.registration, container, false)
                registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
                binding.registrationViewModel = registrationViewModel
                registrationViewModel.onUserRegistration.observe(viewLifecycleOwner, Observer { response ->
                        resolveUserRegistration(response)
                })

                binding.lifecycleOwner = viewLifecycleOwner

                return binding.root
        }


        private fun resolveUserRegistration(response: ServerResponse) {
                when (response){
                        ServerResponse.SERVER_SUCCESS -> {
                                this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                                registrationViewModel.userRegistered()
                        }
                        ServerResponse.SERVER_ERROR -> {
                                Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT).show()
                                registrationViewModel.userRegistered()
                        }
                        ServerResponse.PASSWORD_MISMATCH -> {
                                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                                registrationViewModel.userRegistered()
                        }
                        ServerResponse.USER_ALREADY_EXISTS -> {
                                Toast.makeText(context, "User with this username has already been registered!", Toast.LENGTH_SHORT).show()
                                registrationViewModel.userRegistered()
                        }
                        else -> {}
                }

        }

}
