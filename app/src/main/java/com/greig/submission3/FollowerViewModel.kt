package com.greig.submission3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greig.submission3.entity.GUData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowerViewModel: ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<GUData>>()

    fun setFollower(username: String?) {

        val listItems = ArrayList<GUData>()
        val url = "https://api.github.com/users/$username/followers"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token d7a4e72e72c9b701de6ab26491152a671d07657e")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val follower = responseObject.getJSONObject(i)
                        val followerItems = GUData()
                        followerItems.login = follower.getString("login")
                        followerItems.avatar = follower.getString("avatar_url")
                        followerItems.url = follower.getString("url")
                        listItems.add(followerItems)
                    }
                    listFollowers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Follower Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Follower onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<GUData>> {
        return listFollowers
    }
}