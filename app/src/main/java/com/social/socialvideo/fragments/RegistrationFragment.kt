package com.social.socialvideo.fragments;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.RegistrationBinding
import com.social.socialvideo.utils.PasswordUtil

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
                if(PasswordUtil.checkMatchingPasswords(binding.password.text.toString(), binding.passwordConfirm.text.toString(), this.context)){
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
