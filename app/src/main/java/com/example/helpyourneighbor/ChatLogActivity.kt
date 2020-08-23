package com.example.helpyourneighbor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helpyourneighbor.models.ChatMessage
import com.example.helpyourneighbor.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    lateinit var edt_chat_log : EditText
    val adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        recyclerView_chat_log.adapter = adapter
        recyclerView_chat_log.layoutManager = LinearLayoutManager(this)
        edt_chat_log = findViewById(R.id.editText_chat_log)
        supportActionBar?.title = "Chat Log"
        val announcementOwner = intent.getParcelableExtra<User>(AnnouncementDetailsActivity.USER_KEY)

        txtView_chat_log.text = announcementOwner.userName
        saveUserIdAndUsername(announcementOwner.userId, announcementOwner.userName)
        listenForMessages()
        
        btn_send_chat_log.setOnClickListener(){

            performSendMessage()
        }


    }
//Retrieve messages and show it
    private fun listenForMessages(){
    val announcementOwner = intent.getParcelableExtra<User>(AnnouncementDetailsActivity.USER_KEY)
    val announcementId = intent.getStringExtra("announcementId")
    val fromId = FirebaseAuth.getInstance().uid
    val toId = announcementOwner.userId
    val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")
    //val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$announcementId/$fromId/$toId")
    //Log.d("chat", "avant entrer: ${r}")
        ref.addChildEventListener(object : ChildEventListener{

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                Log.d("chat", "dedans")
                Log.d("chat","Message: ${chatMessage!!.message}")
                //we just need messages related to the concerned announcement
                if (chatMessage != null && chatMessage.announcementId == announcementId){

                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatToRowItem(chatMessage.message))
                    }else {

                        adapter.add(ChatFromRowItem(chatMessage.message))
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }



            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }



    private fun performSendMessage(){
        val announcementOwner = intent.getParcelableExtra<User>(AnnouncementDetailsActivity.USER_KEY)
        val announcementId = intent.getStringExtra("announcementId")
        val fromId = FirebaseAuth.getInstance().uid
        val toId = announcementOwner.userId
        //val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$announcementId/$fromId/$toId").push()
        //val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$announcementId/$toId/$fromId").push()

        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val messageId = reference.key
        val message = editText_chat_log.text.toString()

        val chatMessage = ChatMessage(messageId!!, fromId!!, toId!!, message,announcementId, System.currentTimeMillis() / 1000)
        reference.setValue(chatMessage).addOnSuccessListener {
            Log.d("chat", "ok1")
            //Toast.makeText(this, "success 1", Toast.LENGTH_LONG ).show()
        }
        toReference.setValue(chatMessage).addOnSuccessListener {
            Log.d("chat", "ok2")
            edt_chat_log.clearComposingText()
            //Toast.makeText(this, "success 2", Toast.LENGTH_LONG ).show()
        }

    }

/*    private fun setupData(){

        adapter.add(ChatFromRowItem("ffzhjjjkhfgjsgbkj \n uzguggkkjjk"))
        adapter.add(ChatToRowItem(""))
        adapter.add(ChatFromRowItem(""))
        adapter.add(ChatToRowItem(""))
        adapter.add(ChatFromRowItem(""))
        adapter.add(ChatToRowItem(""))
        adapter.add(ChatToRowItem(""))
        adapter.add(ChatToRowItem(""))


    }*/
private fun saveUserIdAndUsername (toId: String?, username: String){
    //val uid = FirebaseAuth.getInstance().uid
    //val keyForId : String = ""+uid
    val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    val editor : SharedPreferences.Editor = sharedPreferences.edit()
    editor.apply{
        putString("userId", toId)
        putString("username", username)
    }.apply()

}
}

class ChatFromRowItem(val text : String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_from_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}


class ChatToRowItem(val text : String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_to_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}