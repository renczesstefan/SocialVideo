package com.social.socialvideo.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.social.socialvideo.R
import com.social.socialvideo.databinding.PostsBinding
import com.social.socialvideo.domain.UserPost
import com.social.socialvideo.viewModels.PostsViewModel

class PostsFragment : Fragment() {


    private lateinit var binding: PostsBinding
    private lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.posts, container, false
        )
        val activity = requireNotNull(this.activity) {
            "Only no null activity is required"
        }
        postsViewModel = ViewModelProvider(this, PostsViewModel.Factory(activity.application)).get(PostsViewModel::class.java)

        binding.postsViewModel = postsViewModel
        return binding.root
    }

    /**
     * Funkcia ktora je volana po tom ako sa vytvori fragmentova hierarchia
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observujeme livedata ktore vracaju vsetky videa z databazy
        postsViewModel.postList.observe(viewLifecycleOwner, Observer<List<UserPost>> { videos ->
            videos?.apply {
                println("cuck")
            }
        })
    }
}