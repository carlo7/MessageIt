package com.carlos.messageit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var memberRecyclerview: RecyclerView
    private lateinit var memberList: ArrayList<Member>
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var auth : FirebaseAuth
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        dbReference = FirebaseDatabase.getInstance().reference

        memberList = ArrayList()
        memberAdapter = MemberAdapter( this,memberList)
        memberRecyclerview = findViewById(R.id.recyclerview)

        memberRecyclerview.layoutManager =LinearLayoutManager(this)
        memberRecyclerview.adapter = memberAdapter

        dbReference.child("member").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                memberList.clear()

                //implement logic on data change operation to our database
                for (edtSnap in snapshot.children){
                    val currentUser = edtSnap.getValue(Member::class.java)
                    if (currentUser != null && auth.currentUser?.uid != currentUser.uid) {          //Assert that the user/member to be added to the list does have a null value
                                                                                                     //Assert that the currently logged in member does not appear in the view to avoid a solo-chat
                        memberList.add(currentUser)
                    }
                    memberAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //implement the logic behind cancel operation on data
            }

        })


    }
    //inflate options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_option){
            auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)

            return true
        }
        return super.onOptionsItemSelected(item)
    }

}