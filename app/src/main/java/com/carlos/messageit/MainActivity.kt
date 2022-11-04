package com.carlos.messageit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var memberRecyclerview: RecyclerView
    private lateinit var memberList: ArrayList<Member>
    private lateinit var memberAdapter: MemberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memberList = ArrayList()
        memberAdapter = MemberAdapter( this,memberList)
    }
}