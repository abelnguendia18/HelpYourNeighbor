package com.example.helpyourneighbor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_announcement)
        var arrayList = ArrayList<Announcement>()
        arrayList = Helper().retrieveAnnouncementsFromFirebase()
/*        arrayList.add(Model("Titre1", "Description1", R.drawable.chat))
        arrayList.add(Model("Titre2", "Description2", R.drawable.chat))
        arrayList.add(Model("Titre3", "Description3", R.drawable.chat))
        arrayList.add(Model("Titre4", "Description4", R.drawable.chat))
        arrayList.add(Model("Titre5", "Description5", R.drawable.chat))
        arrayList.add(Model("Titre6", "Description6", R.drawable.chat))
        arrayList.add(Model("Titre7", "Description7", R.drawable.chat))
        arrayList.add(Model("Titre8", "Description8", R.drawable.chat))*/

        val myAdapter = MyAdapter(arrayList, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = myAdapter
        return view
    }

}
