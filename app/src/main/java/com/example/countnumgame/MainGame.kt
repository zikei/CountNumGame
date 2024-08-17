package com.example.countnumgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView

class MainGame : AppCompatActivity() {
    private val MAX_HIT_CNT = 3
    private val STOCK_NUM_MAX = 5
    private var stockSize : Int = 21
    private var stock: MutableList<MutableMap<String, String>> = mutableListOf()
    private var players: MutableList<MutableMap<String, String>> = mutableListOf()
    private var playerIdx: Int = 0
    private var cnt = 0
    private var hit_cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        val playerNum = intent.getIntExtra("playerNum",3)
        stockSize = intent.getIntExtra("stockSize", stockSize)

        createPlayer(playerNum)
        createStock(stockSize)

        lvPlayer()
        lvStock()

        showRemainStock()
        showPlayer()
        showNextCard()
    }

    fun onHit(view: View){
        cnt++
        hit_cnt++

        if(cnt >= stockSize) {
            players[playerIdx]["score"] = "-1"
            result()
            return
        }

        if(hit_cnt >= MAX_HIT_CNT){
            findViewById<Button>(R.id.bt_hit).isEnabled = false
        }else if(!findViewById<Button>(R.id.bt_change).isEnabled){
            findViewById<Button>(R.id.bt_change).isEnabled = true
        }

        var num = (stock[cnt-1]["card"]?.toIntOrNull()) ?: 0
        var score = (players[playerIdx]["score"]?.toIntOrNull()) ?: 0
        players[playerIdx]["score"] = (score + num).toString()

        lvStock()
        lvPlayer()

        showRemainStock()
        showNextCard()
    }

    fun onChange(view: View){
        hit_cnt = 0
        playerIdx = (playerIdx+1) % players.size

        findViewById<Button>(R.id.bt_change).isEnabled = false
        findViewById<Button>(R.id.bt_hit).isEnabled = true

        showPlayer()
    }

    private fun result(){
        val result = Intent(this@MainGame, Result::class.java)

        players.sortByDescending { it["score"]?.toIntOrNull() }
        var score: ArrayList<String> = arrayListOf()
        var player: ArrayList<String> = arrayListOf()
        players.forEach{ score.add(it["score"] ?: "0") }
        players.forEach{ player.add((it["name"] ?: ""))}

        result.putStringArrayListExtra("score", score)
        result.putStringArrayListExtra("player", player)

        println(score)

        startActivity(result)
        finish()
    }

    private fun showRemainStock(){
        findViewById<TextView>(R.id.tv_remainStockShow).text = (stock.size - cnt).toString()
    }

    private fun showPlayer(){
        findViewById<TextView>(R.id.tv_playerShow).text = players[playerIdx]["name"]
    }

    private fun showNextCard(){
        findViewById<TextView>(R.id.tv_nextShow).text = stock[cnt]["card"]
    }

    private fun lvStock(){
        val stockList = findViewById<ListView>(R.id.lv_stock)
        val from = arrayOf("idx", "card")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        val adapter = SimpleAdapter(this@MainGame, stock.subList(cnt,stock.size), android.R.layout.simple_list_item_2, from, to)
        stockList.adapter = adapter
    }

    private fun lvPlayer(){
        val playerList = findViewById<ListView>(R.id.lv_score)
        val from = arrayOf("name", "score")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        val adapter = SimpleAdapter(this@MainGame, players, android.R.layout.simple_list_item_2, from, to)
        playerList.adapter = adapter
    }


    private fun createPlayer(playerNum: Int){
        for(i in 1 .. playerNum){
            players.add(mutableMapOf("name" to "プレイヤー$i", "score" to "0"))
        }
    }

    private fun createStock(stockSize: Int){
        for(i in 1 until stockSize){
            stock.add(mutableMapOf("idx" to "$i 枚目","card" to (1..STOCK_NUM_MAX).random().toString()))
        }
        stock.add(mutableMapOf("idx" to "$stockSize 枚目", "card" to "joker"))
    }
}