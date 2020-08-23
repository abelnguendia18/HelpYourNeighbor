package com.example.helpyourneighbor.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.example.helpyourneighbor.LoginActivity
import com.example.helpyourneighbor.adapters.MyAdapter
import com.example.helpyourneighbor.R
import com.example.helpyourneighbor.models.Announcement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object Utils{
  lateinit var dbRef : DatabaseReference
  lateinit var announcementList : MutableList<Announcement>
    lateinit var myAdapter: MyAdapter
  //private lateinit var mActivity : Activity

    fun checkInternetConnection(context: Context) :Boolean{
      val connManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val wifiConn : NetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
      val mobilDataConn : NetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
      return wifiConn.isConnectedOrConnecting || mobilDataConn.isConnectedOrConnecting
    }

  fun alertDialogInternetShower(context: Context) {

    val builder = AlertDialog.Builder(context, R.style.alertTheme)
    builder.setTitle("Internetzugang")
    builder.setIcon(R.drawable.ic_action_warning)
    builder.setMessage("Überprüfen Sie bitte Ihre Internetverbindung.")
    builder.setPositiveButton("Alles klar"){ dialog, which ->
      //finish()
    }
    builder.show()

  }
  fun alertDialogLogoutConfirmation(context: Context) {

    val builder = AlertDialog.Builder(context, R.style.alertTheme)
    builder.setTitle("Logout Confirmation")
    builder.setIcon(R.drawable.ic_action_warning)
    builder.setMessage("Wollen Sie sich wirklich ausloggen?")
    builder.setPositiveButton("Ja"){ dialog, which ->
      //finish()
      val user = FirebaseAuth.getInstance().currentUser
      if (user != null){
        Log.i("Logout", "Logout KO")
      }
      else{
        Log.i("Logout", "Logout OK")
      }
      val intent = Intent(context, LoginActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
      //mActivity.startActivity(intent)
      context.startActivity(intent)

    }
    builder.setNegativeButton("Nein"){dialog, which ->
      
    }
    builder.show()

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