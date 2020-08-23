package com.example.helpyourneighbor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.helpyourneighbor.models.User
import com.example.helpyourneighbor.utils.Utils
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtName : TextInputLayout
    private lateinit var edtPhone : TextInputLayout
    private lateinit var edtEmail : TextInputLayout
    private lateinit var edtPassword : TextInputLayout
    private lateinit var btnSelectPhoto : Button
    private val LOGCAT_NAME = "SignUpActivity "

    override fun onCreate(savedInstanceState: Bundle?) {
        // One way to hide the ActionBar(ColorPrimary and ColorPrimaryDark in styles.xml)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        edtName = findViewById(R.id.editText_sign_up_activity_name)
        edtPhone = findViewById(R.id.editText_sign_up_activity_username)
        edtEmail = findViewById(R.id.editText_sign_up_activity_email)
        edtPassword = findViewById(R.id.editText_sign_up_activity_password)


        btn_create_account_sign_up_activity.setOnClickListener()
        {
            val internetConnectionState : Boolean = Utils.checkInternetConnection(this)
            if(checkName() && checkEmail() && checkPassword()){
                //In case there is no internet connection
                if (!internetConnectionState)
                {
                    //Toast.makeText(this,"Prüfen Sie bitte Ihre Internetverbindung.", Toast.LENGTH_LONG ).show()
                    Utils.alertDialogInternetShower(this)
                    return@setOnClickListener
                }

                saveUser()
            }

        }

//TODO: create a function to check fields
        btn_already_have_account_sign_up_activity.setOnClickListener()
        {
            finish()
        }
    }

//we create a the same time the user and his Login Account(with email and password)
private fun saveUser() {

    val name = edtName.editText?.text.toString().trim()// we cast TextInputLayout into EditText
    val phone = edtPhone.editText?.text.toString().trim()
    val email = edtEmail.editText?.text.toString().trim()
    val password = edtPassword.editText?.text.toString().trim()
    //val dbReference = FirebaseDatabase.getInstance().getReference("users")
    //val userId = dbReference.push().key // to obtain(generate) one unique key for our object
    //val user1 = User(userId , name, username)
    //if (userId != null) {
      /*  dbReference.child(userId).setValue(user1).addOnCompleteListener(){
            Toast.makeText(this, "User created successfully", Toast.LENGTH_LONG).show()
        }*/
        //Creation of Login Account with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successfully
                //We retrieve the ID of the created Account to assign it as UserId
                val uid = FirebaseAuth.getInstance().uid
                val dbReference = FirebaseDatabase.getInstance().getReference("/users/$uid")
                val someUser = User(uid, name, phone)
                savePhoneNumber(phone)
                dbReference.setValue(someUser).addOnCompleteListener(){
                    Toast.makeText(this," User created successfully", Toast.LENGTH_LONG ).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                }
            }
            .addOnFailureListener{
                Log.i(LOGCAT_NAME,"Failed to create the user: ${it.message}")
                Toast.makeText(this,"Überprüfen Sie bitte Ihre Eingaben.", Toast.LENGTH_LONG ).show()
            }
    //}

}
    private fun savePhoneNumber( phoneNumber : String){
        val uid = FirebaseAuth.getInstance().uid
        val key : String = "phoneNumber"+uid
        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString(key, phoneNumber)
        }.apply()

    }

    private fun checkName(): Boolean{
    val name = edtName.editText?.text.toString().trim()
    //val noWhiteSpace = "\\A\\w{4,20}\\z"
    var result = true
    if(name.isEmpty())
    {
        edtName.error ="Please enter your Name"
        result = false
    }
    else{
        edtName.error = null
        edtName.isErrorEnabled = false
        result = true
    }
    return result
}

    private fun checkUsername(): Boolean{
    val username = edtPhone.editText?.text.toString().trim()
    //val noWhiteSpace = "\\A\\w{4,20}\\z"
    var result = true
    if(username.isEmpty())
    {
        edtPhone.error ="Please enter an Username"
        result = false
    }
    else{
        edtPhone.error = null
        edtPhone.isErrorEnabled = false
        result = true
    }
    return result
}

    private fun checkEmail(): Boolean{
    val email = edtEmail.editText?.text.toString().trim()
    //val noWhiteSpace = "\\A\\w{4,20}\\z"
    var result = true
    if(email.isEmpty())
    {
        edtEmail.error ="Please enter your e-mail address"
        result = false
    }
    else{
        edtEmail.error = null
        edtEmail.isErrorEnabled = false
        result = true
    }
    return result
}

    private fun checkPassword(): Boolean{
    val password = edtPassword.editText?.text.toString().trim()
    val noWhiteSpace = "\\A\\w{6,20}\\z".toRegex()
    var result = true
    if(password.isEmpty())
    {
        edtPassword.error ="Please enter your Password"
        result = false
    }
    else if(!password.matches(noWhiteSpace)){
        edtPassword.error = "Please enter at least 6 characters for Password "
        result = false
    }
    else{
        edtPassword.error = null
        edtPassword.isErrorEnabled = false
        result = true
    }
    return result
}

}
