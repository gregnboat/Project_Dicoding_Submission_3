package com.greig.submission3.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.greig.submission3.db.DatabaseContract
import com.greig.submission3.db.DatabaseContract.GithubUserColumns.Companion.CONTENT_URI
import com.greig.submission3.db.GithubUserHelper

class GUProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2

        private lateinit var githubUserHelper: GithubUserHelper
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(
                DatabaseContract.AUTHORITY,
                DatabaseContract.GithubUserColumns.TABLE_NAME, USER)
            uriMatcher.addURI(DatabaseContract.AUTHORITY, "${DatabaseContract.GithubUserColumns.TABLE_NAME}/#", USER_ID)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added : Long = when(USER) {
            uriMatcher.match(uri) -> githubUserHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor : Cursor? = null
        when(uriMatcher.match(uri)) {
            USER -> cursor = githubUserHelper.queryAll()
            USER_ID -> githubUserHelper.queryById(uri.lastPathSegment.toString())

            else -> cursor = null
        }
        return cursor
    }

    override fun onCreate(): Boolean {
        githubUserHelper = GithubUserHelper.getInstance(context as Context)
        githubUserHelper.open()

        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val update : Int = when(USER_ID) {
            uriMatcher.match(uri) -> githubUserHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return update
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val delete = when(USER_ID) {
            uriMatcher.match(uri) -> githubUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return delete
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}