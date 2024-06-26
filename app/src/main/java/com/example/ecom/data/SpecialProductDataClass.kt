package com.example.ecom.data

import android.os.Parcel
import android.os.Parcelable

class SpecialProductDataClass(
    val productName: String?, val productImage: Int, val oldprice: String?, val newprice: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productName)
        parcel.writeInt(productImage)
        parcel.writeString(oldprice)
        parcel.writeString(newprice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpecialProductDataClass> {
        override fun createFromParcel(parcel: Parcel): SpecialProductDataClass {
            return SpecialProductDataClass(parcel)
        }

        override fun newArray(size: Int): Array<SpecialProductDataClass?> {
            return arrayOfNulls(size)
        }
    }
}
