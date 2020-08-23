package com.example.helpyourneighbor.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helpyourneighbor.adapters.MyAdapter
import com.example.helpyourneighbor.R
import com.example.helpyourneighbor.models.Announcement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyAnnouncementsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAnnouncementsFragment : Fragment(), MyAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAnnouncemenstList : MutableList<Announcement>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_my_announcements, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_my_announcement)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        myAnnouncemenstList = mutableListOf()// we need to initialize the list.


        return view
    }

    override fun onStart() {
        val context : Context = activity!!.applicationContext
        super.onStart()

        //recyclerView.adapter = Utils().retrieveAnnouncementsFromFirebase(requireContext())

        val uid = FirebaseAuth.getInstance().uid

        val dbRef = FirebaseDatabase.getInstance().getReference("announcements")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                myAnnouncemenstList.clear() //To avoid duplications
                if(p0!!.exists()){
                    for(h in p0.children){
                        val announcement = h.getValue(Announcement::class.java)
                        if(announcement!!.ownerId == uid)// We just need to have the Announcements of the current user.
                            myAnnouncemenstList.add(announcement!!)
                    }
                    Log.d("HomeFragment", "part3: il y a ${myAnnouncemenstList.size} elements dans la db")
                    val myAdapter = MyAdapter(myAnnouncemenstList, context, this@MyAnnouncementsFragment)
                    recyclerView.adapter = myAdapter
                    // the List will be up to date
                    //(recyclerView.adapter as MyAdapter).notifyDataSetChanged()
                }
            }

        })



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyAnnouncementsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyAnnouncementsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(announcement: Announcement) {}
}