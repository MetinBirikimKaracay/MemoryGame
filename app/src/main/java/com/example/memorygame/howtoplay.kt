package com.example.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_howtoplay.*

class howtoplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_howtoplay)

        onStart()
        imagecard.animate().apply {
            duration = 1000
            rotationYBy(360f)
        }.withEndAction {
            imagecard.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start();
        }


    }
}