package com.carlos.messageit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context : Context, private val messageList: ArrayList<Message> ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var auth : FirebaseAuth
    private var mMessageSent= 1
    private var mMessageReceived = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType==1){

            val view: View = LayoutInflater.from(context).inflate(R.layout.m_sent, parent, false)
            SendViewHolder(view)

        }else{

            val view: View = LayoutInflater.from(context).inflate(R.layout.m_received, parent, false)
            ReceiveViewHolder(view)

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

         if(holder.javaClass == SendViewHolder::class.java){
             val viewHolder = holder as SendViewHolder
             val currentMessage = messageList[position]  //TODO check if initializing current position inside the loop affects the app during execution

             holder.sendMessage.text = currentMessage.message

        }else{
            val viewHolder = holder as ReceiveViewHolder
             val currentMessage = messageList[position]

             holder.receiveMessage.text = currentMessage.message
         }
    }
    override fun getItemViewType(position: Int): Int {
        auth = FirebaseAuth.getInstance()
        val  currentMessage = messageList[position]

        return if (auth.currentUser?.uid == currentMessage.senderID){

            mMessageSent

        }else{
            mMessageReceived
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {

        return messageList.size

    }


    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sendMessage: TextView = itemView.findViewById(R.id.sent_message)

    }
    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val receiveMessage: TextView = itemView.findViewById(R.id.received_message)

    }
}