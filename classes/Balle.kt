package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import kotlin.math.*

class Balle (x1: Float, y1: Float, val diametre: Float) {
    val balle = RectF(x1 - diametre/2, y1 - diametre/2, x1 + diametre/2, y1 + diametre/2)
    val random = Random()
    val paint = Paint()
    var v = 5F
    var dir = PI/2
    var dead : Boolean = false

    init {
        paint.color = Color.WHITE
    }

    fun draw (canvas: Canvas) {
        canvas.drawOval(balle, paint)
    }

    fun paroi_interract(horizontale: Boolean) {
        if (horizontale) {
            this.dir = -dir
        }
        else {
            this.dir = PI-dir
        }
        balle.offset((v*cos(dir)).toFloat(), (-v*sin(dir)).toFloat())
    }


    fun bouge(lesParois: Array<Parois>,deadzone: Float): Boolean {
        balle.offset((v*cos(dir)).toFloat(), (-v*sin(dir)).toFloat())
        if (balle.centerY() > deadzone) {
            this.meure()
        }
        for (p in lesParois) {
            p.gereBalle(this)
        }
        return !dead
    }

    fun barre_interract(new_dir:Double) {
        this.dir = new_dir
        balle.offset((v*cos(dir)).toFloat(), (-v*sin(dir)).toFloat())
    }

    fun meure(){
        dead = true
    }
}