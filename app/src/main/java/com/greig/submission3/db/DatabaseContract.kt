package com.greig.submission3.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.greig.submission3"
    const val SCHEME = "content"

    class GithubUserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "github_user_list"
            const val ID = "id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"
            const val URL = "url"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}