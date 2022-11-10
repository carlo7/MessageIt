package com.carlos.messageit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var inputCardView: CardView
    private lateinit var edtTextField: EditText
    private lateinit var btnEmoji: ImageView
    private lateinit var btnAttachment: ImageView
    private lateinit var btnSend: ImageView
    private lateinit var auth : FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatRecyclerView = findViewById(R.id.chat_recyclerview)
        inputCardView = findViewById(R.id.chat_cardview)
        edtTextField = findViewById(R.id.edt_message1)
        btnEmoji = findViewById(R.id.btn_emoji)
        btnAttachment = findViewById(R.id.btn_attach)
        btnSend = findViewById(R.id.btn_send)
        auth = FirebaseAuth.getInstance()
        dbReference = FirebaseDatabase.getInstance().reference

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        
        supportActionBar?.title = name

    }
}