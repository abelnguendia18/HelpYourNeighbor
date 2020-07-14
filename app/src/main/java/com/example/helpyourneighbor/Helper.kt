package com.example.helpyourneighbor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Adapter
import com.google.firebase.database.*

object Helper{
  lateinit var dbRef : DatabaseReference
  lateinit var announcementList : MutableList<Announcement>
    lateinit var myAdapter: MyAdapter
    fun checkInternetConnection(context: Context) :Boolean{
      val connManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val wifiConn : NetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
      val mobilDataConn : NetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
      return wifiConn.isConnectedOrConnecting || mobilDataConn.isConnectedOrConnecting
    }
    private fun uploadImageToFirebase(){}

/*
   fun retrieveAnnouncementsFromFirebase(context: Context) : MyAdapter{
    announcementList = mutableListOf()
       myAdapter = MyAdapter(announcementList, context)
    val dbRef = FirebaseDatabase.getInstance().getReference("announcements")

    dbRef.addValueEventListener(object : ValueEventListener{
      override fun onCancelled(p0: DatabaseError) {

      }

      override fun onDataChange(p0: DataSnapshot) {
        if(p0!!.exists()){
            for(h in p0.children){
                Log.d("sonson", "avant");
              val announcement = h.getValue(Announcement::class.java)
                Log.d("sonson" ,"${announcement!!.descriptionAnnouncement}");
              announcementList.add(announcement!!)
            }
            Log.d("sonson", "il y a ${announcementList.size} elements dans la db")
            myAdapter = MyAdapter(announcementList, context)
        }
      }

    })
       return myAdapter!!
  }*/


}