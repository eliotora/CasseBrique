package com.example.cassebrique

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class Bonus(x1: Float, y1: Float, context: Context) {
    val random = Random()
    var type = -1
    val v = 100f
    var r : RectF
    val paint = Paint()
    var dead = false
    lateinit var image: Bitmap

    init{
        type = random.nextInt(4)
        when(type){
            0 -> image = BitmapFactory.decodeResource(context.resources, R.drawable.bonuslife) // +1 vie
            1 -> image = BitmapFactory.decodeResource(context.resources, R.drawable.bonusballe) // +1 balle
            2 -> image = BitmapFactory.decodeResource(context.resources, R.drawable.bonusslow) // balle(s) plus lente(s)
            3 -> image = BitmapFactory.decodeResource(context.resources, R.drawable.bonuswide) // barre plus large
        }
        val w = image.width.toFloat()
        val h = image.height.toFloat()
        r = RectF(x1-w/2,y1-h/2,x1+w/2,y1+h/2)
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
        canvas.drawBitmap(image, r.left, r.top,null)
    }
}
