package com.example.helpyourneighbor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_home_page.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNewAnnouncement : Fragment() {
    //lateinit var spinnerKategorie: TextInputLayout
    lateinit var fab : FloatingActionButton
    val options = arrayOf("Putzhilfe", "Einkaufshilfe", "Hundesitter", "Gartenhilfe", "Sonstiges")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new_announcement, container, false)
        //spinnerKategorie = view.findViewById(R.id.spinner_kategorie)
       //spinnerKategorie.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options)

        return  view

    }

}
