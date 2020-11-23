package com.social.socialvideo.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.social.socialvideo.R
import com.social.socialvideo.databinding.ProfileBinding
import com.social.socialvideo.entities.UserInfoResponse
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.services.SessionManager
import com.social.socialvideo.utils.PathUtils
import com.social.socialvideo.viewModels.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL


class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        sessionManager = SessionManager(this.requireContext())
        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.profile, container, false)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        // Get the viewmodel
        profileViewModel = ViewModelProvider(this, ProfileViewModel.Factory(sessionManager.fetchAuthToken().toString())).get(ProfileViewModel::class.java)
        binding.profileViewModel = profileViewModel


        profileViewModel.url.observe(viewLifecycleOwner, Observer { url ->
            if(url != ""){
                refreshProfilePicture(url)
            }
        })

        /**
        * Táto funkcia slúži na otvorenie galérie, z čoho budeme vybrať fotku. 9 znamená,
         * že budeme vybrať fotku/video.
        * */
        profileViewModel.addImage.observe(viewLifecycleOwner, Observer { addImage ->
            if(addImage){
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(intent, 9)
            }
        })

        profileViewModel.onLogout.observe(viewLifecycleOwner, Observer { logout ->
            if(logout) {
                resolveUserLogout(logout)
                profileViewModel.resetOnLogout()
            }
        })

        profileViewModel.onPasswordChange.observe(viewLifecycleOwner, Observer { changePwd ->
            if(changePwd) {
                this.findNavController()
                    .navigate(R.id.action_profileFragment_to_postsFragment)
                profileViewModel.resetOnPasswordChange()
            }
        })

        profileViewModel.uploadStatus.observe(viewLifecycleOwner, Observer { status ->
            if(status != ServerResponse.DEFAULT) {
                resolveUploadStatus(status)
                profileViewModel.resetState()
            }
        })

        return binding.root
    }

    private fun resolveUploadStatus(status: ServerResponse?) {
        when (status){
            ServerResponse.SERVER_SUCCESS -> {
                Toast.makeText(context, "Profile picture was successfully!", Toast.LENGTH_SHORT).show()
            }
            ServerResponse.SERVER_ERROR -> {
                Toast.makeText(context, "Upload of profile picture failed!", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun resolveUserLogout(logout: Boolean) {
        val sessionManager = SessionManager(this.requireContext())
        sessionManager.deleteToken()
        this.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    /**
     * Automaticky sa spustí po vybratí fotky. Spracováva dáta vybranej fotky, a spustí upload
     * pomocou profileViewModelu
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 9 && resultCode == RESULT_OK && data != null){
            val imageUri: Uri = data.data!!
            val path: String = PathUtils.getPath(this.requireContext(), imageUri)!!
            profileViewModel.uploadProfilePic(path, sessionManager.fetchAuthToken()!!)
        }
    }

    private fun refreshProfilePicture(url: String){
        Glide.with(this)
            .load(url).apply(RequestOptions.skipMemoryCacheOf(true))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .into(binding.profileImage)
    }

}