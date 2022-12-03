package com.carlos.messageit

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var editName : EditText
    private lateinit var btnRegister : Button
    private lateinit var dbReference : DatabaseReference
    private  lateinit var progressBar : ProgressBar
    private lateinit var accountReady : TextView

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        editName = findViewById(R.id.edt_name)
        btnRegister = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.reg_pro_bar)
        accountReady = findViewById(R.id.account_ready)
        accountReady.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
        
        btnRegister.setOnClickListener {

            //call register function on click
            register()
        }
    }

    private fun register() {
        //initialize input variables and trim to assume existence of whitespaces
        val name = editName.text.toString().trim()
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString().trim()

        //Perform validations
        if (name.isEmpty()){
            editName.error = "User name is required"
            editName.requestFocus()
            return

        }
        if (email.isEmpty()){
            editEmail.error = "Email is required"
            editEmail.requestFocus()
            return

        }
        if (password.isEmpty()){
            editPassword.error = "Password is required"
            editPassword.requestFocus()
            return

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.error = "Provide a valid email"
            editEmail.requestFocus()
            return
        }
        if (password.length < 6){
            editPassword.error = "Provide a password of more than 6 characters"
            editPassword.requestFocus()
            return
        }
        //set progressbar visibility to true
        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Register success, show tag then start Main activity
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(this, "Registration Successful. Please Login",
                        Toast.LENGTH_SHORT).show()
                    
                    //Call function to add member on complete provision of validation details
                    auth.currentUser?.let { addMemberToDB(name, email, it.uid) }

                    progressBar.visibility = View.GONE
                    
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    finish()
                    startActivity(intent)


                } else {
                    // If registration fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Registration failed. Try again",
                        Toast.LENGTH_SHORT).show()

                    progressBar.visibility = View.GONE

                }
            }

    }
    //Write members/users to database
    private fun addMemberToDB(name: String, email: String, uid: String) {
        dbReference = FirebaseDatabase.getInstance().reference
        dbReference.child("member").child(uid).setValue(Member(name, email, uid))
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.account_ready->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}