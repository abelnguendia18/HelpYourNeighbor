package com.example.helpyourneighbor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtName : TextInputLayout
    private lateinit var edtUsername : TextInputLayout
    private lateinit var edtEmail : TextInputLayout
    private lateinit var edtPassword : TextInputLayout
    private val LOGCAT_NAME = "SignUpActivity "

    override fun onCreate(savedInstanceState: Bundle?) {
        // One way to hide the ActionBar(ColorPrimary and ColorPrimaryDark in styles.xml)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        edtName = findViewById(R.id.editText_sign_up_activity_name)
        edtUsername = findViewById(R.id.editText_sign_up_activity_username)
        edtEmail = findViewById(R.id.editText_sign_up_activity_email)
        edtPassword = findViewById(R.id.editText_sign_up_activity_password)

        btn_create_account_sign_up_activity.setOnClickListener()
        {
            if(checkName() && checkUsername() && checkEmail() && checkPassword()){

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
    val username = edtUsername.editText?.text.toString().trim()
    val email = edtEmail.editText?.text.toString().trim()
    val password = edtPassword.editText?.text.toString().trim()
    // The reference users is considered  like a table of the database
    val dbReference = FirebaseDatabase.getInstance().getReference("users")
    val userId = dbReference.push().key // to obtain(generate) one unique key for our object
    val user1 = User(userId , name, username, email, password)
    if (userId != null) {
        dbReference.child(userId).setValue(user1).addOnCompleteListener(){
            Toast.makeText(this, "User created successfully", Toast.LENGTH_LONG).show()
        }
        //Creation of Login Account with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successfully
                Toast.makeText(this," User Create successful", Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener{
                Log.i(LOGCAT_NAME,"Failed to create the user: ${it.message}")
                Toast.makeText(this,"Failed to create the User", Toast.LENGTH_LONG ).show()
            }
    }

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
    val username = edtUsername.editText?.text.toString().trim()
    //val noWhiteSpace = "\\A\\w{4,20}\\z"
    var result = true
    if(username.isEmpty())
    {
        edtUsername.error ="Please enter an Username"
        result = false
    }
    else{
        edtUsername.error = null
        edtUsername.isErrorEnabled = false
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
