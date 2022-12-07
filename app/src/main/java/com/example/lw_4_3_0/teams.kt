package com.example.lw_4_3_0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class teams : AppCompatActivity() {

    var playersList = ArrayList<Player>()
    private lateinit var recV:RecyclerView
    private lateinit var adapter: Player_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        val intent = intent
        val count = intent.getIntExtra("countPresent", -1)
        var name:String
        var img:String
        var player:Player
        for (i in 0 until count) {
            name = intent.getStringExtra("footballername$i")!!
            img = intent.getStringExtra("footballerimg$i")!!
            player = Player(name, img.toInt(), true, 0)
            playersList.add(player)
        }
        initRecyclerView()
        distributeFootballers()
    }

    private fun initRecyclerView() {
        recV = findViewById(R.id.mRecyclerView)
        adapter = Player_Adapter(this, playersList)
        recV.adapter = adapter
        recV.layoutManager = LinearLayoutManager(this)
    }

    private fun distributeFootballers() {
        var size = playersList.size
        var len:Int = 0
        if (size.mod(2)==0)
        {
            len = size
        }
        else
        {
            len = size - 1
        }

        for (i in 0 until len){
            if (i.mod(2)==0){
                playersList[i].team = 1
            }
            else{
                playersList[i].team = 2
            }
        }
        adapter.notifyDataSetChanged()
    }
}