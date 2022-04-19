package com.example.memorygame

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.memorygame.R.drawable.*
import kotlinx.android.synthetic.main.activity_hard.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class hard : AppCompatActivity() {

    var pictures:MutableList<Int> = mutableListOf(
        bubbles,coyote,dexter, ededdeddy, gadget, jerry, jhonnybravo, leonardo, marsupilami, rafael,
        redkit, samuraijack, smurf, smurfdad, splintermaster, spongebob,tom, transformers,
        bubbles,coyote,dexter, ededdeddy, gadget, jerry, jhonnybravo, leonardo, marsupilami, rafael,
        redkit, samuraijack, smurf, smurfdad, splintermaster, spongebob,tom, transformers,
    )
    //Eşleşen kartları saklamak için oluşturduğumuz boş Mutable List
    var numb:MutableList<Int> = mutableListOf(

    )
    var buttons:Array<ImageButton> = arrayOf();
    var db  = DataBaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard)
        textView3.text = "$success/18"

        buttons = arrayOf(img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15,img16,img17,
            img18,img19,img20,img21,img22,img23,img24,img25,img26,img27,img28,img29,img30,img31,img32,img33,img34,img35,img36)

        pictures.shuffle()

        object : CountDownTimer(120000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                textView2.text = (millisUntilFinished / 1000).toString()    //Kalan süreyi yazar
                gameTime = 121 - (millisUntilFinished / 1000).toInt()    //Geçen süreyi hesaplar

                if(control == 1){   //Oyun kazanıldığında çalışır
                    onPause()
                }
            }

            override fun onFinish() {   //Süre bittiğinde çalışır
                gameOverControl(1)
                textView2.text = "Bitti"
            }

            fun onPause() = cancel()

        }.start()

    }
    var success = 0     //Başarılı eşleşme sayısı
    var firstCard = 0   //Açılan ilk kartın tagi
    var secondCard = 0  //Açılan ikinci kartın tagi
    var counter = 0     //Hamle sayısı
    var control = 0     //Oyun bittiğinde süreyi durdurmak için
    var score = 0       //Oyuncunun puanı
    var gameTime = 0    //Oyunda geçen zaman
    var penalty = 0     //Gereksiz döndür butonu clicklerinde ceza yemeyi önlemek için
    var gameMode = 2

    //Oyunun sonucunu kontrol eder
    fun gameOverControl(a:Int){

        val builder= AlertDialog.Builder(this)
        if(a == 0){     //Tüm kartlar eşleştiğinde çalışır
            score += 180
            builder.setTitle("Tebrikler!!")
            builder.setMessage("Puanınız "+score+"\nGeçen Süre "+gameTime+" saniye")

            var scores = Scores(score,gameMode,gameTime)
            db.insertData(scores)


        }else if(a == 1){   //Süre bittiğinde çalışır
            builder.setTitle("Süreniz Doldu!!")
            for(i in 0..35){
                buttons[i].isClickable = false
            }
        }
        //Açılan pop-up'taki şıklar
        builder.setPositiveButton("Tekrar Oyna") { dialogInterface: DialogInterface, i: Int ->
            finish()    //Bu olmazsa yeni oyun eski pencerenin üstüne açılır
            val intent = Intent(applicationContext,hard::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("Ana Menü") { dialogInterface: DialogInterface, i: Int ->
            var data = db.readData()
            tvHard.text = ""
            for(i in 0 until data.size){
                tvHard.append("Puanınız : "+data.get(i).score.toString()+" Süre : "+data.get(i).gameTime+"\n")
            }
            finish()    //Açık olan pencereyi kapatır
        }
        builder.show()
    }

    fun onClick(view: View){
        var button : ImageButton = view as ImageButton
        var tag = button.tag.toString().toInt()
        button.setImageResource(pictures[tag])

        counter++

        if(counter % 2 == 1 ){
            firstCard = tag
        }else if (counter % 2 == 0){
            secondCard = tag
            matchControl()
        }
        textView1.text = counter.toString()

    }

    fun matchControl(){
        if(firstCard == secondCard){    //Açık karta tıklandığında hamle sayısını arttırmamayı sağlar
            secondCard = 20
            counter--
        }else if(pictures[firstCard].toInt() == pictures[secondCard].toInt()){

            success++
            textView3.text = "$success/18"
            numb.add(firstCard)
            numb.add(secondCard)
            buttons[firstCard].isClickable = false
            buttons[secondCard].isClickable = false

            if(success == 18){
                gameOverControl(0)
                control = 1
            }
            //Eşleşme olmadıysa daha fazla kart açılmaması için tüm kartları kilitler
        }else if(pictures[firstCard].toInt() != pictures[secondCard].toInt()){
            penalty = 1

            for(i in 0..35){
                buttons[i].isClickable = false
            }
        }
    }

    fun flipCards(view: View) {

        if(penalty == 1){
            score -= 2
            penalty = 0
        }

        for(i in 0..35){
            if(i in numb){  //Daha önceden eşleşmiş kartların dönmesini engeller
                continue
            }else{
                buttons[i].isClickable = true   //Eşleşmemiş kartları tıklanabilir hale getirir
                buttons[i].setImageResource(card_background)
                buttons[i].setImageResource(card_background)

            }
        }
    }
}