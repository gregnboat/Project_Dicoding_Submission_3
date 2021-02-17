package com.greig.submission3

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.title = resources.getString(R.string.listFavorite)

        showRecyclerView()
        componentSearch()
    }

    private fun showRecyclerView() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()



        val list = LinearLayoutManager(this)
        rvUser.layoutManager = list
        rvUser.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_settings -> {
                val mIntent = Intent(applicationContext, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.setting_for_search_list -> {
                val sIntent = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(sIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun componentSearch() {

        //Declare
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchUser.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchUser.queryHint = "Cari User"

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.setUsername(query)
                MA_progressBar.visibility = View.VISIBLE
                return true
            }

            override fun onQueryTextChange(resText: String): Boolean {
                return false
            }
        })

        mainViewModel.getUsername().observe(this, Observer { listItem ->
            if (listItem != null){
                adapter.setData(listItem)
                MA_progressBar.visibility = View.GONE
            }
        })
    }
}