package com.example.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var db  = DataBaseHelper(this)
        var data = db.readData()
        tvEasy.text = ""
        tvHard.text = ""
        for(i in 0 until data.size){
            if(data.get(i).gameMode == 1){
                tvEasy.append("Puanınız : "+data.get(i).score.toString()+" - Oyun Süresi : "+data.get(i).gameTime.toString()+"\n")
            }else if(data.get(i).gameMode == 2){
                tvHard.append("Puanınız : "+data.get(i).score.toString()+" - Oyun Süresi : "+data.get(i).gameTime.toString()+"\n")
            }
        }
    }
    fun change_activity(view: View){
        val intent = Intent(applicationContext,easylvl::class.java)
        startActivity(intent) /*
            Click on the easy button to switch to the easy game from the main page.
            */}
    fun change_activity_2(view: View){
        val intent = Intent(applicationContext,hard::class.java)
        startActivity(intent) /*
                Click on the hard button to switch to the hard game from the main page.
                */}
    fun change_activity_3(view: View){
        val intent = Intent(applicationContext,howtoplay::class.java)
        startActivity(intent) /*
                Click on the how to play button to go to the how-to-play screen to learn how to play
                */}


}