package com.greig.submission3.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.AVATAR_URL
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.ID
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.TABLE_NAME
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.URL
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.USERNAME

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "githubuserapp"

        private const val DATABASE_VERSION = 1

        private  const val SQL_CREATE_TABLE_GITHUB_USER = "CREATE TABLE $TABLE_NAME" +
                " (${ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${USERNAME} TEXT NOT NULL," +
                " ${AVATAR_URL} TEXT NOT NULL," +
                " ${URL} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GITHUB_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}