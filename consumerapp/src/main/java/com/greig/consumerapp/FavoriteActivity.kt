package com.greig.consumerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.greig.consumerapp.db.DatabaseContract.GithubUserColumns.Companion.CONTENT_URI
import com.greig.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.listFavorite)


        if (savedInstanceState == null) {
            loadUserAsync()
        }

        rvFavorite.layoutManager = LinearLayoutManager(this)


    }

    private fun loadUserAsync() {
        GlobalScope.launch (Dispatchers.Main) {
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToList(cursor)
            }

            val user = deferredUser.await()
            if (user.isNotEmpty()) {
                rvFavorite.adapter = FavoriteAdapter(applicationContext, user)
            } else {
                noDataText.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bar_settings -> {
                val mIntent = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}