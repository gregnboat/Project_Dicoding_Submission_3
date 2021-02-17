package com.greig.submission3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greig.submission3.FollowerAdapter
import com.greig.submission3.FollowerViewModel
import com.greig.submission3.R
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {

    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var adapter: FollowerAdapter

    companion object {
        const val EXTRA_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollower.setHasFixedSize(true)
        showListFollower()

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        followerViewModel.getFollowers().observe(viewLifecycleOwner, Observer { followersItem ->
            if (followersItem != null) {
                adapter.setData(followersItem)
                progress_bar.visibility = View.GONE
            }
        })

        if (arguments != null) {
            val username = arguments?.getString(EXTRA_USERNAME)
            followerViewModel.setFollower(username)
        }
    }

    private fun showListFollower() {
        rvFollower.layoutManager = LinearLayoutManager(activity)
        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()
        rvFollower.adapter = adapter
    }

}