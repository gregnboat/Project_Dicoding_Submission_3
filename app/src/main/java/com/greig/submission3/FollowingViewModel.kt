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

class FollowingViewModel: ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<GUData>>()

    fun setFollowing(username: String?) {

        val listItems = ArrayList<GUData>()
        val url = "https://api.github.com/users/$username/following"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token d7a4e72e72c9b701de6ab26491152a671d07657e")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val following = responseObject.getJSONObject(i)
                        val followingItems = GUData()
                        followingItems.login = following.getString("login")
                        followingItems.avatar = following.getString("avatar_url")
                        followingItems.url = following.getString("url")
                        listItems.add(followingItems)
                    }
                    listFollowing.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Following Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int,
                                   headers: Array<out Header>?,
                                   responseBody: ByteArray?,
                                   error: Throwable?
            ) {
                Log.d("Following onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<GUData>> {
        return listFollowing
    }
}