package com.social.socialvideo.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.RegistrationBinding


class RegistrationFragment : Fragment() {

        private lateinit var binding: RegistrationBinding
        private lateinit var registrationViewModel: RegistrationViewModel


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
                // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
                binding = DataBindingUtil.inflate(
                        inflater, R.layout.registration, container, false)
                // Get the viewmodel
                registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
                binding.registrationViewModel = registrationViewModel


                registrationViewModel.onUserRegistration.observe(viewLifecycleOwner, Observer { isSuccess ->
                        resolveUserRegistration(isSuccess)
                })

                binding.lifecycleOwner = viewLifecycleOwner

                return binding.root
        }


        private fun resolveUserRegistration(isSuccess: Boolean) {
                if (isSuccess) {
                        this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                        registrationViewModel.userRegistered()
                }
        }



}
