package com.example.memorygame

import java.util.*

class Scores {

    var id:Int = 0
    var score:Int = 0
    var gameMode:Int = 0
    var gameTime:Int = 0

    constructor(score:Int, gameMode:Int,gameTime:Int) {
        this.score = score
        this.gameMode = gameMode
        this.gameTime = gameTime


    }

    constructor() {}

}