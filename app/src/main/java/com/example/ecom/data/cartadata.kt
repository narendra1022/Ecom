package com.example.ecom.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class cartadata(
    val id: String,
    val name: String,
    val category: String,
    val price: String,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors: List<Int>? = null,
    val sizes: List<String>? = null,
    val images: List<String>?
):Parcelable{
    constructor() : this("","","","",0f,"",null,null,null)
}
