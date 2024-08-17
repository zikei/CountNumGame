package com.example.countnumgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter

class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getStringArrayListExtra("score") ?: arrayListOf()
        val player = intent.getStringArrayListExtra("player") ?: arrayListOf()

        var result = player.zip(score){ a,b ->
            mutableMapOf("name" to a, "score" to b)
        }

        val resultList = findViewById<ListView>(R.id.lv_result)
        val from = arrayOf("name", "score")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        val adapter = SimpleAdapter(this@Result, result, android.R.layout.simple_list_item_2, from, to)
        resultList.adapter = adapter
    }

    fun onEnd(view: View){
        finish()
    }
}