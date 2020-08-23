package com.example.helpyourneighbor.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helpyourneighbor.AnnouncementDetailsActivity
import com.example.helpyourneighbor.adapters.MyAdapter
import com.example.helpyourneighbor.R
import com.example.helpyourneighbor.models.Announcement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/* Once the registration was successful, the user is redirected here. */
class HomeFragment : Fragment(), MyAdapter.OnItemClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var announcementList : MutableList<Announcement>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_announcement)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        announcementList = mutableListOf()// we need to initialize the list.


        return view
    }

    override fun onStart() {
        val context : Context = activity!!.applicationContext
        super.onStart()

        //recyclerView.adapter = Utils().retrieveAnnouncementsFromFirebase(requireContext())

        val uid = FirebaseAuth.getInstance().uid

        val dbRef = FirebaseDatabase.getInstance().getReference("announcements")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                    announcementList.clear() //To avoid duplications
                if(p0!!.exists()){
                    for(h in p0.children){
                        val announcement = h.getValue(Announcement::class.java)
                        if(announcement!!.ownerId != uid)// One user don't need to see his announcements in the list on HomePage.
                        announcementList.add(announcement!!)
                    }
                    Log.d("HomeFragment", "part3: il y a ${announcementList.size} elements dans la db")
                    val myAdapter = MyAdapter(announcementList, context, this@HomeFragment)
                    recyclerView.adapter = myAdapter
                    // the List will be up to date
                    //(recyclerView.adapter as MyAdapter).notifyDataSetChanged()
                }
            }

        })



    }

    override fun onItemClick(announcement: Announcement) {

        //Toast.makeText(requireContext(), announcement.ownerPhoneNumber , Toast.LENGTH_LONG ).show()
        val intent = Intent(context, AnnouncementDetailsActivity::class.java)
        intent.putExtra("someAnnouncement",announcement)
        intent.putExtra("announcementStatus", announcement.status)
        intent.putExtra("announcementDescription", announcement.descriptionAnnouncement)
        intent.putExtra("announcementOwnerId", announcement.ownerId)
        intent.putExtra("announcementPrice", announcement.priceAnnouncement)
        intent.putExtra("announcementOwnerPhoneNumber", announcement.ownerPhoneNumber)

        startActivity(intent)
        //(this as Activity).finish()
        //this.activity!!.finish()
        //this.finish()
    }


}
