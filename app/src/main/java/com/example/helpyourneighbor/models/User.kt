package com.example.helpyourneighbor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
//class User(val userId : String?, val name : String, val username : String, val email : String, val password : String)
class User(val userId : String?, val userName : String, val userTelNumber : String): Parcelable{

    constructor() : this ("", "", "")
}