package com.greig.submission3.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GUData(
    var id: Int = 0,
    var login: String? = null,
    var avatar: String? = null,
    var url: String? = null,
    var name: String? = null,
    var location: String? = null,
    var company: String? = null,
    var repository: String? = null,
    var followers: String? = null,
    var following: String? = null
) : Parcelable