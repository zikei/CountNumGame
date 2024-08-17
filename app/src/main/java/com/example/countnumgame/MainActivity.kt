package com.example.countnumgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onStartClick(view: View){
        val playerNum = findViewById<EditText>(R.id.en_player).text.toString().toIntOrNull()
        val stockSize = findViewById<EditText>(R.id.en_stockSize).text.toString().toIntOrNull()

        if(playerNum == null || playerNum < 2){
            Toast.makeText(this@MainActivity, getString(R.string.playerNumErr), Toast.LENGTH_LONG).show()
            return
        }
        if(stockSize == null || stockSize < 1){
            Toast.makeText(this@MainActivity, getString(R.string.stockSizeErr), Toast.LENGTH_LONG).show()
            return
        }

        val mainGame = Intent(this@MainActivity, MainGame::class.java)

        mainGame.putExtra("playerNum", playerNum)
        mainGame.putExtra("stockSize", stockSize)

        startActivity(mainGame)
    }
}