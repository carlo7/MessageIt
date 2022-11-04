package com.carlos.messageit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(val context: Context,val memberList:ArrayList<Member>):
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
    }

    //Count members and return size of type integer
    override fun getItemCount(): Int {

        return memberList.size
    }
    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvMessage = itemView.findViewById<TextView>(R.id.tv_message)

    }

}