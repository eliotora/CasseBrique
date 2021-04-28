package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Bonus(x1: Float, y1: Float) {
    val random = Random()
    var type = -1
    val v = 100f
    val r = RectF(x1-20f, y1-20f,x1+20f,y1+20f)
    val paint = Paint()
    var dead = false

    init{
        type = random.nextInt(4)
        when(type){
            0 -> paint.color = Color.RED // +1 vie
            1 -> paint.color = Color.WHITE // +1 balle
            2 -> paint.color = Color.BLUE // balle(s) plus lente(s)
            3 -> paint.color = Color.GREEN // barre plus large
        }
    }

    fun updatePos(dTime: Double, laBarre: Barre): Int {
        r.offset(0f, (v*dTime).toFloat())
        if (r.intersect(laBarre.r)) {
            dead = true
            return type
        }
        else return -1
    }

    fun draw(canvas: Canvas) {
        canvas.drawOval(r, paint)
    }
}