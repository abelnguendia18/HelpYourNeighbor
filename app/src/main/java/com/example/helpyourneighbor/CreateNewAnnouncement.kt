package com.example.helpyourneighbor

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.helpyourneighbor.models.Announcement
import com.example.helpyourneighbor.utils.Utils
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNewAnnouncement : Fragment() {
    lateinit var textField_kategorie: AutoCompleteTextView
    //lateinit var edtTitle : TextInputLayout
    lateinit var  edtPrice : TextInputLayout
    lateinit var edtDescription : TextInputLayout
    lateinit var edtAddress : TextInputLayout
    lateinit var btnSelectPhoto : Button
    lateinit var btnCreateNewAnnouncement: Button
    lateinit var radioGroup : RadioGroup
    lateinit var tv : TextView
    lateinit var status : String
    lateinit var category : String
    //different categories
    val items = arrayOf("Putzhilfe", "Einkaufshilfe", "Hundesitter-Hilfe", "Gartenhilfe", "Sonstiges")
    val LOG_NAME ="CreateNewAnnouncement"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new_announcement, container, false)

        //tv = view.findViewById(R.id.tv_test)
        btnSelectPhoto = view.findViewById(R.id.btn_selectPhoto_new_announcement)
        textField_kategorie = view.findViewById(R.id.autoCompleteTV_categorie_of_announcement)
        btnCreateNewAnnouncement = view.findViewById(R.id.btn_create_new_announcement)
        //edtTitle = view.findViewById(R.id.editText_titel_anzeige)
        edtPrice = view.findViewById(R.id.editText_kategorie_preis)
        edtAddress = view.findViewById(R.id.editText_adresse)
        edtDescription = view.findViewById(R.id.editText_beschreibung)
        radioGroup = view.findViewById(R.id.radioGroup)


        val uid = FirebaseAuth.getInstance().uid
        //Toast.makeText(context,"$uid",Toast.LENGTH_LONG ).show()
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
            //Toast.makeText(context,"your photo is ready...",Toast.LENGTH_LONG ).show()
        }


        btnCreateNewAnnouncement.setOnClickListener(){
            val internetConnectionState : Boolean = Utils.checkInternetConnection(requireContext())
            if (!internetConnectionState){
                Utils.alertDialogInternetShower(context!!)
                //Toast.makeText(context,"Überprüfen Sie bitte Ihre Internetverbindung.",Toast.LENGTH_LONG ).show()
                return@setOnClickListener
            }


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
                addOnSuccessListener { it ->
                    Log.d(LOG_NAME,"successfully uploaded image : ${it.metadata?.path}")
                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(LOG_NAME,"file location : $it")
                        saveAnnouncementToFirebaseDatabase(it.toString())
                    }

                }
    }


    private fun saveAnnouncementToFirebaseDatabase(imagePath : String){
        val context : Context = activity!!.applicationContext
        val ownerId = FirebaseAuth.getInstance().uid ?: ""// empty String in case the value is null
        //val dbRef = FirebaseDatabase.getInstance().getReference("/announcements/$uid")
        //val title = edtTitle.editText?.text.toString().trim()
        val price = edtPrice.editText?.text.toString().trim()
        val address = edtAddress.editText?.text.toString().trim()
        val description = edtDescription.editText?.text.toString().trim()
        val key : String = "phoneNumber"+ownerId
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val ownerPhoneNumber = sharedPreferences.getString(key, null)
        val dbRef = FirebaseDatabase.getInstance().getReference("/announcements/")
        val announcementId = dbRef.push().key // to obtain(generate) one unique key to store our object

        if(announcementId != null)
        {
            val announcement = Announcement(announcementId, imagePath, status, category, price, address, description, ownerId, ownerPhoneNumber!!)
            dbRef.child(announcementId).setValue(announcement).addOnSuccessListener {
                Toast.makeText(context, "Ihre Anzeige wurde erfolgreich erstellt", Toast.LENGTH_LONG).show()
                //HomePageActivity().loadFragment(HomeFragment())
                val intent = Intent(context, HomePageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }

/*        dbRef.setValue(announcement).
                addOnSuccessListener {
                    Log.d(LOG_NAME,"Announcement created successfully")
                }*/
    }

}
