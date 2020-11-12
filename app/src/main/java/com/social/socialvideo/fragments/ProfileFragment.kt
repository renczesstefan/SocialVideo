package com.social.socialvideo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.ProfileBinding
import java.lang.NullPointerException

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.profile, container, false)
        binding.profileModel = this

        return binding.root
    }

    fun onLogoutClick() {
        logout()
        this.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    fun onChangePasswordClick() {
        this.findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
    }

    private fun logout() {
        TODO("Not yet implemented")
    }

}