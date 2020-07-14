package com.example.helpyourneighbor

class Announcement(val announcementId : String, val imagePath : String, val status : String, val categoryAnnouncement : String, val priceAnnouncement: String,
                   val ownerAddress : String, val descriptionAnnouncement: String, val ownerId : String, val ownerPhoneNumber : String){

    constructor() : this ("","","","","",
                            "", "","", ""){

    }

    fun announcementDetails(){
        print("${this.announcementId}\t ${this.imagePath}\t ${this.status}\t ${this.categoryAnnouncement}\t" +
                "${this.priceAnnouncement}\t ${this.descriptionAnnouncement}\t ${this.ownerId}\t")
    }
}