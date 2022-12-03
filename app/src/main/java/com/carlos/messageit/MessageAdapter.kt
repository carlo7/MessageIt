package com.carlos.messageit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context : Context, val messageList: ArrayList<Message> ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     private val M_SENT = 1
     private val M_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder : RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == 1){
            val v1: View = inflater.inflate(R.layout.m_sent, parent, false)
            viewHolder = SendViewHolder(v1)
        }else{
            val v2: View = inflater.inflate(R.layout.m_received, parent, false)
            viewHolder = ReceiveViewHolder(v2)

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

         if(holder.javaClass == SendViewHolder::class.java){

             val viewHolder = holder as SendViewHolder

             holder.sendMessage.text = currentMessage.message

        }else{
            val viewHolder = holder as ReceiveViewHolder

             holder.receiveMessage.text = currentMessage.message
         }
    }
    override fun getItemViewType(position: Int): Int {


        val  currentMessage = messageList[position]

        if ( FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID) ){

           return M_SENT

        }else{
           return M_RECEIVED
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