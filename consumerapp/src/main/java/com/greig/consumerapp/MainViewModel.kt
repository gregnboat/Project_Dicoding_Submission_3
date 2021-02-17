package com.greig.consumerapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greig.consumerapp.entity.GUData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<GUData>>()

    fun setUsername(username: String){
        val listUser = ArrayList<GUData>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.apply {
            addHeader("Authorization", "token d7a4e72e72c9b701de6ab26491152a671d07657e")
            addHeader("User-Agent", "request")
            get(url, object : AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
                ) {
                    try{
                        val result = String(responseBody)
                        Log.d(MainActivity.TAG, result)
                        val responseObject = JSONObject(result)
                        val items = responseObject.getJSONArray("items")

                        for (i in 0 until items.length()){
                            val item = items.getJSONObject(i)
                            val username = item.getString("login")
                            val url = item.getString("html_url")
                            val avatar = item.getString("avatar_url")
                            val userItems = GUData()
                            userItems.login = username
                            userItems.url = url
                            userItems.avatar = avatar
                            listUser.add(userItems)
                        }
                        listUsers.postValue(listUser)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable
                ) {
                    Log.d("onFailure", error.message.toString())
                }
            })
        }
    }

    fun getUsername(): LiveData<ArrayList<GUData>> {
        return listUsers
    }
}