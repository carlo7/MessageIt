package com.carlos.messageit

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private lateinit var progressbar : ProgressBar
    private lateinit var forgotPassword :TextView

    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)
        progressbar = findViewById(R.id.prog_bar)
        forgotPassword = findViewById(R.id.forgot_password)
        
        forgotPassword.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        btnLogin.setOnClickListener {


            login()
            
        }

    }


    private fun login() {

       val email = editEmail.text.toString().trim()
       val password = editPassword.text.toString().trim()

       //Check for Empty fields
       if(email.isEmpty()){
           editEmail.error = "Please Enter Email"
           editEmail.requestFocus()
           return
       }
       if(password.isEmpty()){
           editPassword.error = "Please Enter Password"
           editPassword.requestFocus()
           return
       }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.error = "Provide a valid email"
            editEmail.requestFocus()
            return
        }

        //set Progress bar visibility to true
        progressbar.visibility = View.VISIBLE

       //Cross-check fields to assert correct details for sign in entered
       auth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener(this) { task ->
               if (task.isSuccessful) {
                   // login success, update UI with the signed-in user's information
                   Log.d(TAG, "signInWithEmail:success")
                   Toast.makeText(this, "Login Successful",
                       Toast.LENGTH_SHORT).show()
                   progressbar.visibility = View.GONE
                   val intent = Intent(this, MainActivity::class.java)
                   finish()
                   startActivity(intent)

               } else {
                   // If login fails, display a message to the user.
                   Log.w(TAG, "signInWithEmail:failure", task.exception)
                   Toast.makeText(this, "Please enter correct details.",
                       Toast.LENGTH_SHORT).show()
                   progressbar.visibility = View.GONE

               }
           }
    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.forgot_password->{

           }
       }
    }
}