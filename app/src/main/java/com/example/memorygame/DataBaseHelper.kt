package com.example.memorygame

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name = "MemoryGameDB"
val table_name = "Kayitlar"
var col_id = "id"
var col_score = "score"
var col_gameMode = "GameMode"
var col_gameTime = "GameTime"


class DataBaseHelper (var context: Context): SQLiteOpenHelper(context,
    database_name,
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        //veritabanı oluştuğunda bir kez çalışır

        var createTable = " CREATE TABLE "+ table_name+"("+
                col_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col_score+" INTEGER,"+
                col_gameMode+" INTEGER,"+
                col_gameTime+" INTEGER)"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //veritabanında bir update yapmak için kullanılır
    }

    //veri kaydetmek için tanımladığımız fonksiyon

    fun insertData(scores: Scores){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_score, scores.score)
        cv.put(col_gameMode, scores.gameMode)
        cv.put(col_gameTime, scores.gameTime)

        var sonuc = db.insert(table_name, null, cv)
        if(sonuc == (-1).toLong()){
//            Toast.makeText(context, "Hatalı", Toast.LENGTH_LONG).show()
        }else{
//            Toast.makeText(context, "Başarılı", Toast.LENGTH_LONG).show()
        }
    }

    fun readData():MutableList<Scores> {
        var liste:MutableList<Scores> = ArrayList()
        val db = this.readableDatabase
        var query = "Select * from "+ table_name+" ORDER BY "+ col_id+" DESC"
        var result = db.rawQuery(query,null)

        if(result.moveToFirst()) {
            do {
                var scores = Scores()
                scores.id = result.getString(result.getColumnIndexOrThrow(col_id)).toInt()
                scores.score = result.getString(result.getColumnIndexOrThrow(col_score)).toInt()
                scores.gameMode = result.getString(result.getColumnIndexOrThrow(col_gameMode)).toInt()
                scores.gameTime = result.getString(result.getColumnIndexOrThrow(col_gameTime)).toInt()

                liste.add(scores)

            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return liste

    }
}