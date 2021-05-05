package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import kotlin.collections.ArrayList

class Brique (x1: Float, y1: Float, x2: Float, y2: Float, v: CasseBriqueView): Parois(x1, y1, x2, y2) {
    var sprite = RectF(r.left+2f, r.top+2f, r.right-2f, r.bottom-2f)
    var random = Random()
    var color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    var resistance: Int = 3
    var dead = false
    val view= v

    init {
        changeCouleur()
    }

    fun changeCouleur() {
        when(resistance) {
            3 -> paint.color = Color.YELLOW
            2 -> paint.color = Color.argb(255, 255,140,0)
            1 -> paint.color = Color.RED
            else -> Color.BLACK
        }
    }

    override fun gereBalle(b: Balle, dTime: Double): Boolean {
        var collision = super.gereBalle(b, dTime)
        if (collision) {
            resistance -=1
            if (resistance <= 0) meure()
        }
        changeCouleur()
        return collision
    }

    override fun draw(canvas: Canvas) {
        changeCouleur()
        canvas.drawRect(sprite, paint)
    }

    fun meure(){
        dead = true
        if (random.nextDouble()> 0.66f) view.createBonus(this)
    }

     fun resize(rec: RectF) {
         r = rec
         sprite = RectF(r.left+2f, r.top+2f, r.right-2f, r.bottom-2f)
     }
}
