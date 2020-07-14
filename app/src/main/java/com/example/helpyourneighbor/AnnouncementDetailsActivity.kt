package com.example.helpyourneighbor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AnnouncementDetailsActivity : AppCompatActivity() {
    lateinit var tvStatus : TextView
    lateinit var tvDescription : TextView
    lateinit var tvPrice : TextView
    lateinit var btnCall : ImageButton
    lateinit var btnChat : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_details)

        tvStatus = findViewById(R.id.statusTv_details_announcement)
        tvDescription = findViewById(R.id.descriptionTv_details_announcement)
        tvPrice = findViewById(R.id.priceTv_details_announcement)
        btnCall = findViewById(R.id.btn_call_details_announcement)
        btnChat = findViewById(R.id.btn_chat_details_announcement)
        tvStatus.text = intent.getStringExtra("announcementStatus")
        tvDescription.text = intent.getStringExtra("announcementDescription")
        tvPrice.text = intent.getStringExtra("announcementPrice")+" Euro/Stunde"

        btnCall.setOnClickListener(){
/*            val uid = FirebaseAuth.getInstance().uid
            val key : String = "phoneNumber"+uid
            val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedString : String? = sharedPreferences.getString(key, null)*/
           val savedString = intent.getStringExtra("announcementOwnerPhoneNumber")
            //Toast.makeText(this,"Wollen Sie wirklich anrufen???", Toast.LENGTH_LONG ).show()
            Toast.makeText(this, savedString.toString() , Toast.LENGTH_LONG ).show()
        }

        btnChat.setOnClickListener(){
            Toast.makeText(this,"Wollen Sie wirklich eine Nachricht senden???", Toast.LENGTH_LONG ).show()
        }
    }
}