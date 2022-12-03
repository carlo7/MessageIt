package com.carlos.messageit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var edtTextField: EditText
    private lateinit var btnSend: ImageView
    private lateinit var auth : FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter

    var senderRoom: String? = null
    var receiverRoom:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //Receive intent from MemberAdapter to display the clicked member name
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid+ senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        chatRecyclerView = findViewById(R.id.chat_recyclerview)
        edtTextField = findViewById(R.id.edt_message1)
        btnSend = findViewById(R.id.btn_send)
        dbReference = FirebaseDatabase.getInstance().reference

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


        dbReference.child("text_messages").child(senderRoom!!)
            .child("chats").addValueEventListener(object:ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(Message::class.java)

                            messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                   //add logic
                }
            })

        btnSend.setOnClickListener{

            val message = edtTextField.text.toString()
            val messageObject = Message(message, senderUid)

            dbReference.child("text_messages")
                .child(senderRoom!!)
                .child("chats").push()
                .setValue(messageObject).addOnSuccessListener{
                    dbReference.child("text_messages")
                        .child(receiverRoom!!)
                        .child("chats").push()
                        .setValue(messageObject)
                }

            edtTextField.setText("")// clear the input field after message has been sent

        }
    }
}