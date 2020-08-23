package com.example.helpyourneighbor.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helpyourneighbor.R
import com.example.helpyourneighbor.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment() {

    lateinit var  recyclerView_chats : RecyclerView
    val adapter = GroupAdapter<ViewHolder>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView_chats = view.findViewById(R.id.recyclerView_chats)

        //adapter.add(UserItem())
        //adapter.add(UserItem())
        //adapter.add(UserItem())
        recyclerView_chats.adapter = adapter
        recyclerView_chats.layoutManager = LinearLayoutManager(context)
        listenFormMessages()
        adapter.setOnItemClickListener{item, view ->
            //val intent = Intent(view.context, ChatLogActivity::class.java)
            //startActivity(intent)
            Toast.makeText(context, "coucou" , Toast.LENGTH_LONG ).show()

        }

        return view
    }

    private fun listenFormMessages(){
        val sharedPreferences : SharedPreferences = context!!.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val toId = sharedPreferences.getString("userId", null)
        val username = sharedPreferences.getString("username", null)
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")
        ref.addChildEventListener(object : ChildEventListener{

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                Log.d("ChatListe", "avant entrer")
                if (chatMessage != null){
                    adapter.add(UserItem(username!!, chatMessage.message))
                   // Log.d("ChatListe", "${chatMessage.message}")
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

    class UserItem(val username : String, val message : String) : Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.textView_username_chat.text = username
            viewHolder.itemView.textView_message_chat.text = message

        }

        override fun getLayout(): Int {
            return R.layout.user_row_new_message
        }
    }

}
