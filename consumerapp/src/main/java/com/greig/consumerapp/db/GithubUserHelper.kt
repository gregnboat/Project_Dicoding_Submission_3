package com.greig.consumerapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.greig.consumerapp.db.DatabaseContract.GithubUserColumns.Companion.TABLE_NAME
import com.greig.consumerapp.db.DatabaseContract.GithubUserColumns.Companion.USERNAME
import java.sql.SQLException

class GithubUserHelper (context: Context){

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: GithubUserHelper? = null

        fun getInstance(context: Context): GithubUserHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: GithubUserHelper(context)
                }

    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun deleteById(username: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }

    fun check(username: String): Boolean {
        val selectId = "SELECT * FROM $DATABASE_TABLE WHERE $USERNAME = ?"
        val cursor = database.rawQuery(selectId, arrayOf(username))
        var check = false

        if (cursor.moveToFirst()) {
            check = true
            var i = 0
            while (cursor.moveToNext()) {
                i++
            }
            Log.d(Constraints.TAG, String.format("%d records found", i))
        }
        cursor.close()
        return check
    }




}