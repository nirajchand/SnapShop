package com.example.individual_project.model

import android.os.Parcel
import android.os.Parcelable

data class ProductModel(
    var productID : String = "",
    var productName : String = "",
    var productDesc : String = "",
    var price : Int = 0,
    var imageUrl: String = ""

) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readString() ?:"",
        parcel.readInt(),
        parcel.readString() ?:"",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productID)
        parcel.writeString(productName)
        parcel.writeString(productDesc)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}