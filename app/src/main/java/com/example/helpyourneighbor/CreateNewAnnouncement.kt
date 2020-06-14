package com.example.helpyourneighbor

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.fragment_create_new_announcement.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNewAnnouncement : Fragment() {
    lateinit var textField_kategorie: AutoCompleteTextView
    lateinit var edtTitle : TextInputLayout
    lateinit var  edtPrice : TextInputLayout
    lateinit var edtDescription : TextInputLayout
    lateinit var btnSelectPhoto : Button
    lateinit var btnCreateNewAnnouncement: Button
    lateinit var radioGroup : RadioGroup
    lateinit var tv : TextView
    lateinit var status : String
    lateinit var category : String
    val items = arrayOf("Putzhilfe", "Einkaufshilfe", "Hundesitter", "Gartenhilfe", "Sonstiges")
    val LOG_NAME ="CreateNewAnnouncement"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new_announcement, container, false)

        tv = view.findViewById(R.id.tv_test)
        btnSelectPhoto = view.findViewById(R.id.btn_selectPhoto_new_announcement)
        textField_kategorie = view.findViewById(R.id.autoCompleteTV_categorie_of_announcement)
        btnCreateNewAnnouncement = view.findViewById(R.id.btn_create_new_announcement)
        edtTitle = view.findViewById(R.id.editText_titel_anzeige)
        edtPrice = view.findViewById(R.id.editText_kategorie_preis)
        edtDescription = view.findViewById(R.id.editText_beschreibung)
        radioGroup = view.findViewById(R.id.radioGroup)


        val uid = FirebaseAuth.getInstance().uid
        Toast.makeText(context,"$uid",Toast.LENGTH_LONG ).show()
        Log.d(LOG_NAME, uid.toString())
        val adapter = ArrayAdapter<String>(requireContext(), R.layout.list_item, items)
        textField_kategorie.setAdapter(adapter)
        textField_kategorie.threshold = 1

        textField_kategorie.onItemClickListener = AdapterView.OnItemClickListener{_, _, position, _ ->
            category = textField_kategorie.text.toString()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioButton_biete)
            {
                status = "Ich biete"
            }
            if(checkedId == R.id.radioButton_suche)
            {
                status = "Ich suche"
            }
        }


        btnSelectPhoto.setOnClickListener(){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
            Toast.makeText(context,"your photo is ready...",Toast.LENGTH_LONG ).show()
        }


        btnCreateNewAnnouncement.setOnClickListener(){
            //Toast.makeText(context,tv_test.text.toString(),Toast.LENGTH_LONG ).show()
            uploadImageToFirebase()
        }

        return  view

    }
    var selectedPhotoUri : Uri? = null

    //This function is executed after we selected one picture(btnSelectPhoto).
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d(LOG_NAME,"photo selected")

            selectedPhotoUri = data.data
            val resolver : ContentResolver = activity!!.contentResolver
            val bitmap = MediaStore.Images.Media.getBitmap(resolver, selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            btnSelectPhoto.background = bitmapDrawable
        }
    }

    private fun uploadImageToFirebase(){
        if(selectedPhotoUri == null)return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!).
                addOnSuccessListener {
                    Log.d(LOG_NAME,"successfully uploaded image : ${it.metadata?.path}")
                    saveAnnouncementToFirebaseDatabase(it.toString())
                }
    }


    private fun saveAnnouncementToFirebaseDatabase(imagePath : String){
        val ownerId = FirebaseAuth.getInstance().uid ?: ""// empty String in case the value is null
        //val dbRef = FirebaseDatabase.getInstance().getReference("/announcements/$uid")
        val dbRef = FirebaseDatabase.getInstance().getReference("/announcements/")
        val announcementId = dbRef.push().key // to obtain(generate) one unique key to store our object
        val title = edtTitle.editText?.text.toString().trim()
        val price = edtPrice.editText?.text.toString().trim()
        val description = edtDescription.editText?.text.toString().trim()
        if(announcementId != null)
        {
            val announcement = Announcement(announcementId, imagePath, status, title, category, price, description, ownerId)
            dbRef.child(announcementId).setValue(announcement).addOnSuccessListener {
                Log.d(LOG_NAME,"Announcement created successfully")
            }
        }

/*        dbRef.setValue(announcement).
                addOnSuccessListener {
                    Log.d(LOG_NAME,"Announcement created successfully")
                }*/
    }


}
