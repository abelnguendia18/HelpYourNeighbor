package com.example.helpyourneighbor.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.helpyourneighbor.R
import com.example.helpyourneighbor.models.User
import com.example.helpyourneighbor.utils.Utils
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
 * Use the [MyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var btnLogout : Button
    private lateinit var userName : TextView
    private lateinit var userPhoneNumber : TextView
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
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)
        userName = view.findViewById(R.id.userName)
        userPhoneNumber = view.findViewById(R.id.userPhoneNumber)
        btnLogout = view.findViewById(R.id.btn_logout)
        val uid = FirebaseAuth.getInstance().uid
        Log.i("Logout", "$uid")
        val dbRef = FirebaseDatabase.getInstance().getReference("users")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (h in p0.children){
                        val user = h.getValue(User::class.java)
                        if (user!!.userId == uid){ // We want to display information about the current user.
                            userName.text = user.userName
                            userPhoneNumber.text = user.userTelNumber
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        btnLogout.setOnClickListener(){
            val internetConnectionState : Boolean = Utils.checkInternetConnection(context!!)
            if (!internetConnectionState) // No internet connection
            {
                Utils.alertDialogInternetShower(context!!)
                return@setOnClickListener
            }
            Utils.alertDialogLogoutConfirmation(context!!)
            FirebaseAuth.getInstance().signOut()
        /*            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                Log.i("Logout", "Logout KO")
            }
            else{
                Log.i("Logout", "Logout OK")
            }*/
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}