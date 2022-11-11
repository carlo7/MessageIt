package com.carlos.messageit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MemberAdapter(val context: Context, private val memberList:ArrayList<Member>):
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>( ) {

    //Inflate member layout to the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
       val view: View = LayoutInflater.from(context).inflate(R.layout.member_layout, parent, false)
        return MemberViewHolder(view)
    }

    //Bind  the inputs to the view

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentUser = memberList[position]

        holder.tvMessage.text = currentUser.name

        holder.itemView.setOnClickListener() {

            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", currentUser.name)                                         //Extra information to place the toolbar of the concerned activity
            intent.putExtra("uid", currentUser.uid)

            context.startActivity(intent)

        }
    }

    //Count members and return size of type integer
    override fun getItemCount(): Int {

        return memberList.size
    }
    //Passing the tv_message of type TextView from member_layout to tvMessage variable
    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvMessage: TextView = itemView.findViewById(R.id.tv_message)

    }

}