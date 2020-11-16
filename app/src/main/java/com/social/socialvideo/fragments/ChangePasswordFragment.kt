package com.social.socialvideo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.ChangePasswordBinding
import com.social.socialvideo.utils.PasswordUtil

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: ChangePasswordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.change_password, container, false)
        binding.changePasswordModel = this

        return binding.root
    }

    fun onChangeClicked(){
        if (PasswordUtil.checkMatchingPasswords(binding.password.text.toString(), binding.passwordConfirm.text.toString())) {
            this.findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)
        } else {
            binding.password.text.clear()
            binding.passwordConfirm.text.clear()
        }
    }
}