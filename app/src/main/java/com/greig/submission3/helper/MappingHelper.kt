package com.greig.submission3.helper

import android.database.Cursor
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.AVATAR_URL
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.ID
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.URL
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.USERNAME
import com.greig.submission3.entity.GUData

object MappingHelper {

    fun mapCursorToList(cursor: Cursor?) : List<GUData> {
        val listUser = ArrayList<GUData>()

        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar_url = getString(getColumnIndexOrThrow(AVATAR_URL))
                val url = getString(getColumnIndexOrThrow(URL))
                listUser.add(GUData(id, username, avatar_url, url))
            }
        }
        return listUser
    }
}