package com.carlos.messageit

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var editName : EditText
    private lateinit var btnRegister : Button
    private lateinit var dbReference : DatabaseReference

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        editName = findViewById(R.id.edt_name)
        btnRegister = findViewById(R.id.btn_register)

        auth = FirebaseAuth.getInstance()
        
        btnRegister.setOnClickListener(){
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val name = editName.text.toString()
            
            register(name,email, password)
        }
    }

    private fun register(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Register success, show tag then start Main activity
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(this, "Registration failed. Try again",
                        Toast.LENGTH_SHORT).show() 
                    
                    //Call function on successful registration
                    auth.currentUser?.let { addMemberToDB(name,email, it.uid) }
                    
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)


                } else {
                    // If registration fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Registration failed. Try again",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }
    //Add member member(s) to Firebase dB
    private fun addMemberToDB(name: String, email: String, uid: String) {
        dbReference = FirebaseDatabase.getInstance().reference
        dbReference.child("member").child(uid).setValue(Member(name, email, uid))
    }
}