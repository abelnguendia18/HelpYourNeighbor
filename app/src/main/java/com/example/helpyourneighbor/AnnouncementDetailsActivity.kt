package com.example.helpyourneighbor

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.helpyourneighbor.models.Announcement
import com.squareup.picasso.Picasso

class AnnouncementDetailsActivity : AppCompatActivity() {
    lateinit var tvStatus : TextView
    lateinit var tvDescription : TextView
    lateinit var tvPrice : TextView
    lateinit var imageViewDetail : ImageView
    lateinit var btnCall : ImageButton
    lateinit var btnChat : ImageButton
    val REQUEST_PHONE_CALL = 1
    lateinit var phone : String

    companion object{
        val USER_KEY = "USER_Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement_details)

        val context = this.applicationContext
        tvStatus = findViewById(R.id.statusTv_details_announcement)
        tvDescription = findViewById(R.id.descriptionTv_details_announcement)
        imageViewDetail = findViewById(R.id.imageIv_details_announcement)
        tvPrice = findViewById(R.id.priceTv_details_announcement)
        btnCall = findViewById(R.id.btn_call_details_announcement)
        btnChat = findViewById(R.id.btn_chat_details_announcement)
        tvStatus.text = intent.getStringExtra("announcementStatus")
        tvDescription.text = intent.getStringExtra("announcementDescription")
        tvPrice.text = intent.getStringExtra("announcementPrice")+" Euro/Stunde"
        val receivedAnnouncement : Announcement = intent.getParcelableExtra<Announcement>("someAnnouncement")
        val ownerId = receivedAnnouncement.ownerId
        val phoneNumber2 = receivedAnnouncement.ownerPhoneNumber
        Picasso.get().load(receivedAnnouncement.imagePath).into(imageViewDetail)
        //val ownerId = intent.getStringExtra("announcementOwnerId")
        val phoneNumber = intent.getStringExtra("announcementOwnerPhoneNumber")

        btnCall.setOnClickListener(){
/*            val uid = FirebaseAuth.getInstance().uid
            val key : String = "phoneNumber"+uid
            val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedString : String? = sharedPreferences.getString(key, null)*/

            //intent.putExtra("announcementOwnerId", announcement.ownerId)

            phone = phoneNumber
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            }else{
                startCall(phoneNumber)
            }
            //Toast.makeText(this,"Wollen Sie wirklich anrufen???", Toast.LENGTH_LONG ).show()
            //Toast.makeText(this, phoneNumber.toString() , Toast.LENGTH_LONG ).show()
        }

        /*btnChat.setOnClickListener(){

            val ref = FirebaseDatabase.getInstance().getReference("/users")
            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach{
                        val user = it.getValue(User::class.java)
                        //we perform this action in order to have the owner's username of the current announcement
                        //userId(commes from users) has the same value with ownerId(commes from announcements)
                        if(user?.userId == receivedAnnouncement.ownerId)
                        {
                            val intent = Intent(context, ChatLogActivity::class.java)
                            intent.putExtra(USER_KEY, user)
                            intent.putExtra("announcementId",receivedAnnouncement.announcementId)
                            startActivity(intent)
                           //Toast.makeText(context, ownerId.toString(), Toast.LENGTH_LONG ).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("InfoBD", error.toString())
                }
            })
            //val intent = Intent(this, ChatLogActivity::class.java)
            //startActivity(intent)
            //Toast.makeText(this, ownerId.toString(), Toast.LENGTH_LONG ).show()
        }*/
        btnChat.setOnClickListener(){
            //smsSender("")
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
            val messageObject = receivedAnnouncement.descriptionAnnouncement
            intent.putExtra("smsBody", messageObject)
            startActivity(intent)
            Toast.makeText(this,"$messageObject", Toast.LENGTH_LONG ).show()
        }
    }

/*    private fun smsSender(messageObject: CharSequence?) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$"))
        intent.putExtra("smsBody", messageObject)
        startActivity(intent)
    }*/

    @SuppressLint("MissingPermission")
    private fun startCall(phoneNumber : String ){
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_PHONE_CALL)startCall(phone)
    }
}