package com.carlos.messageit

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button

    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            login(email, password) 
            
        }

    }
   /** public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }*/

    private fun login(email: String, password: String) {
       auth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener(this) { task ->
               if (task.isSuccessful) {
                   // login success, update UI with the signed-in user's information
                   Log.d(TAG, "signInWithEmail:success")
                   Toast.makeText(this, "Login Successful",
                       Toast.LENGTH_SHORT).show()
                   val intent = Intent(this,MainActivity::class.java)
                   finish()
                   startActivity(intent)

               } else {
                   // If login fails, display a message to the user.
                   Log.w(TAG, "signInWithEmail:failure", task.exception)
                   Toast.makeText(this, "Please enter correct details.",
                       Toast.LENGTH_SHORT).show()

               }
           }
    }
}