package com.example.helpyourneighbor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail : TextInputLayout
    private lateinit var edtPassword : TextInputLayout
    //private lateinit var dbReference: DatabaseRefere
    private val  LOGCAT_NAME ="LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        // One way to hide the ActionBar(ColorPrimary and ColorPrimaryDark in styles.xml)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail = findViewById(R.id.editText_login_activity_email)
        edtPassword = findViewById(R.id.editText_login_activity_password)

        btn_sign_in.setOnClickListener(){
            if(checkEmail() && checkPassword()){
                loginPerformed()
            }
        }

        btn_sign_up.setOnClickListener(){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }




    private fun loginPerformed(){
        val email = edtEmail.editText?.text.toString().trim()
        val password = edtPassword.editText?.text.toString().trim()
        Log.i(LOGCAT_NAME, "email: $email")
        Log.i(LOGCAT_NAME, "password $password")
        //Firebase Authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successfully
                //Toast.makeText(this," Login successful",Toast.LENGTH_LONG ).show()
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.i(LOGCAT_NAME,"Failed to Log In: ${it.message}")
                //Toast.makeText(this,"Failed to create the User",Toast.LENGTH_LONG ).show()
            }
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
}
