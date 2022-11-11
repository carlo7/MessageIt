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
import com.google.firebase.database.*

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

    var senderRoom: String? = null
    var receiverRoom:String? = null

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

        //Receive intent from MemberAdapter to display the clicked member name
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

         val senderUid = auth.currentUser?.uid
        
        supportActionBar?.title = name

        senderRoom = receiverUid+ senderUid
        receiverRoom = senderUid + receiverUid

        messageList.clear()

        dbReference.child("text_messages").child(senderRoom!!)
            .child("sent_messages").addValueEventListener(object:ValueEventListener{
                /**
                 * This method will be called with a snapshot of the data at this location. It will also be called
                 * each time that data changes.
                 *
                 * @param snapshot The current data at the location
                 */
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (edtSnapshot in snapshot.children){

                        val message = edtSnapshot.getValue(Message::class.java)

                        if (message != null) {
                            messageList.add(message)
                        }

                        messageAdapter.notifyDataSetChanged()

                    }
                }

                /**
                 * This method will be triggered in the event that this listener either failed at the server, or
                 * is removed as a result of the security and Firebase Database rules. For more information on
                 * securing your data, see: [ Security
 * Quickstart](https://firebase.google.com/docs/database/security/quickstart)
                 *
                 * @param error A description of the error that occurred
                 */
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        btnSend.setOnClickListener(){

            val message = edtTextField.text.toString()
            val messageObject = Message(message, senderUid)

            dbReference.child("text_messages").child(senderRoom!!).push()
                .child("sent_messages").setValue(messageObject).addOnSuccessListener(){
                    dbReference.child("text_messages").child(receiverRoom!!).push()
                        .child("receive_messages").setValue(messageObject)
                }
        }

        edtTextField.setText("")

    }
}