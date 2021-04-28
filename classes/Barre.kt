package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import kotlin.math.PI
import kotlin.math.atan

class Barre (x0: Float, y1: Float, width: Float, v: CasseBriqueView): Parois(x0-width/2, y1, x0+width/2, y1 + 50F) {
    var sprite = RectF(x0-width/2, y1, x0+width/2, y1+20f)
    var x: Float = x0
    val y = y1
    var w = width
    val view = v
    var wideT = 0.0


    init{
        paint.color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        if (wideT > 0.0) {
            r = RectF(x-w*1.5f/2, y, x+w*1.5f/2, y+50f)
            sprite = RectF(x-w*1.5f/2, y, x+w*1.5f/2, y+20f)
        }
        else {
            r = RectF(x-w/2, y, x+w/2, y+50f)
            sprite = RectF(x-w/2, y, x+w/2, y+20f)
        }
        canvas.drawRect(sprite, paint)
    }

    fun move(motion: MotionEvent) {
        when(motion.action){
            MotionEvent.ACTION_DOWN -> {
                x = motion.rawX
            }
            MotionEvent.ACTION_MOVE -> {
                x = motion.rawX
            }
        }
    }

    override fun gereBalle(b: Balle, dTime: Double): Boolean {
        var flag = false
        if (RectF.intersects(r, b.balle)){
            flag = true
            var new_dir = 0.0
            val alpha = atan((b.balle.centerY()- r.centerY())/(b.balle.centerX()-r.centerX())).toDouble()
            if (b.balle.centerX() > r.centerX()) {
                new_dir = -alpha
            }
            if (b.balle.centerX() < r.centerX()) {
                new_dir = PI - alpha
            }
            if (b.balle.centerX() == r.centerX()) {
                if (b.balle.centerY() - r.centerY() > 0) {
                    new_dir = -PI/2
                }
                else {
                    new_dir = PI/2
                }
            }
            if (new_dir>= 0) view.text = "Angle = " + new_dir.toString()
            b.barre_interract(new_dir, dTime)
        }
        return flag
    }

    fun update(dTime: Double) {
        wideT -= dTime
    }

    fun wide() {
        wideT = 10.0
    }
}
