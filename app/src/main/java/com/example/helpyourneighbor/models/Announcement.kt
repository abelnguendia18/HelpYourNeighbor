package com.example.helpyourneighbor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Announcement(val announcementId : String, val imagePath : String, val status : String, val categoryAnnouncement : String, val priceAnnouncement: String,
                   val ownerAddress : String, val descriptionAnnouncement: String, val ownerId : String, val ownerPhoneNumber : String): Parcelable{

    constructor() : this ("","","","","",
                            "", "","", ""){

    }

    fun announcementDetails(){
        print("${this.announcementId}\t ${this.imagePath}\t ${this.status}\t ${this.categoryAnnouncement}\t" +
                "${this.priceAnnouncement}\t ${this.descriptionAnnouncement}\t ${this.ownerId}\t")
    }
}