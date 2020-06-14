package com.example.helpyourneighbor

import com.google.firebase.database.*

class Helper {
  lateinit var dbRef : DatabaseReference
  lateinit var announcementList : ArrayList<Announcement>
  private  fun checkInternetConnection(){}
    private fun uploadImageToFirebase(){}

   fun retrieveAnnouncementsFromFirebase() : ArrayList<Announcement>{
    announcementList = arrayListOf()
    val dbRef = FirebaseDatabase.getInstance().getReference("announcements")

    dbRef.addValueEventListener(object : ValueEventListener{
      override fun onCancelled(p0: DatabaseError) {

      }

      override fun onDataChange(p0: DataSnapshot) {
        if(p0!!.exists()){
            for(h in p0.children){
              val announcement = h.getValue(Announcement::class.java)
              announcementList.add(announcement!!)
            }
        }
      }

    })
    return announcementList
  }
}