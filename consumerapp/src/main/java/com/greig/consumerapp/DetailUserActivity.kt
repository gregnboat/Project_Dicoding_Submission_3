package com.greig.consumerapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.greig.consumerapp.db.DatabaseContract
import com.greig.consumerapp.db.GithubUserHelper
import com.greig.consumerapp.entity.GUData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class DetailUserActivity : AppCompatActivity() {

    private var isFavorite = false
    private lateinit var githubUserHelper: GithubUserHelper

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        supportActionBar?.elevation = 0f

        val user = intent.getParcelableExtra<GUData>(EXTRA_USER) as GUData
        val username = user?.login.toString()

        getDataDetail(username)

        val sectionsPagerAdapter = FSPagerAdapter(this, supportFragmentManager)
        //Mengirim username ke pager Adapter
        sectionsPagerAdapter.setUsername(username.toString())
        da_vp_tab.adapter = sectionsPagerAdapter
        da_tab.setupWithViewPager(da_vp_tab)

        setStatusFavorite(isFavorite)

        githubUserHelper = GithubUserHelper.getInstance(applicationContext)
        githubUserHelper.open()

        if (githubUserHelper.check(user?.login.toString())) {
            isFavorite = true
            setStatusFavorite(isFavorite)
        }

        fab_favorite.setOnClickListener {
            if (!isFavorite) {
                isFavorite = !isFavorite

                val values = ContentValues().apply {
                    put(DatabaseContract.GithubUserColumns.USERNAME, user?.login)
                    put(DatabaseContract.GithubUserColumns.AVATAR_URL, user?.avatar)
                    put(DatabaseContract.GithubUserColumns.URL, user?.url)
                }


            } else {

                isFavorite = false
                githubUserHelper.deleteById(user?.login.toString())
                setStatusFavorite(isFavorite)
                Toast.makeText(this, "${user?.login} have been delete from Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite){
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        }
    }



    private fun getDataDetail(usernamed: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$usernamed"
        client.apply {
            addHeader("Authorization", "token d7a4e72e72c9b701de6ab26491152a671d07657e")
            addHeader("User-Agent", "request")
            get(url, object : AsyncHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    try {
                        Log.d("Berhasil getApi", result)
                        val item = JSONObject(result)

                        val userItems = GUData().apply {

                            login = item.getString("login")
                            name = item.getString("name")
                            location = item.getString("location")
                            company = item.getString("company")
                            repository = item.getString("public_repos")
                            followers = item.getString("followers")
                            following = item.getString("following")
                            avatar = item.getString("avatar_url")
                        }

                        supportActionBar?.title = userItems.name

                        tv_name.text = userItems.name
                        tv_login.text = userItems.login
                        tv_location.text =  userItems.location
                        tv_company.text = userItems.company
                        tv_repos.text = userItems.repository
                        Glide.with(this@DetailUserActivity)
                            .load(userItems.avatar)
                            .apply(RequestOptions().override(300, 300))
                            .into(avatar)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                    MA_progressBar.visibility = View.INVISIBLE
                    Log.d("onFailure", error.message.toString())
                }
            })
        }
    }
}