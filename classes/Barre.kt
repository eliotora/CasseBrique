package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import kotlin.math.PI
import kotlin.math.atan

class Barre (x0: Float, y1: Float, width: Float): Parois(x0-width/2, y1, x0+width/2, y1 + 20F) {
    var x: Float = x0
    val y = y1
    var w = width


    init{
        paint.color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        r = RectF(x-w/2, y, x+w/2, y+20f)
        canvas.drawRect(r, paint)
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

    override fun gereBalle(b: Balle): Boolean {
        var flag = false
        if (RectF.intersects(r, b.balle)){
            flag = true
            var new_dir = 0.0
            val alpha = atan((b.balle.centerY()- r.centerY())/(b.balle.centerX()-r.centerX())).toDouble()
            if (b.balle.centerX() > r.centerX()) {
                new_dir = alpha
            }
            if (b.balle.centerX() < r.centerX()) {
                new_dir = PI - alpha
            }
            if (b.balle.centerX() == r.centerX()) {
                if (b.balle.centerY() - r.centerY() > 0) {
                    new_dir = PI/2
                }
                else {
                    new_dir = -PI/2
                }
            }
            b.barre_interract(new_dir)
        }
        return flag
    }

    /*fun activeBonus(bo: Bonus) {
        if (RectF.intersects(barre, bo.icon)) {
        //une icone par bonus
        icon.active //todo
        }
    }*/

}