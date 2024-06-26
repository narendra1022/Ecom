package com.example.ecom.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class addressData(
    var name: String? = null,
    var line1: String? = null,
    var line2: String? = null,
    var district: String? = null,
    var city: String? = null,
    var state: String? = null,
    var pincode: String? = null
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "")
}
