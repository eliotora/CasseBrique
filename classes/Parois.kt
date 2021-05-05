package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

open class Parois (x1: Float, y1: Float, x2: Float, y2: Float) {
    var r = RectF(x1, y1, x2, y2)
    val paint = Paint()

    init{paint.color = Color.GREEN}

    open fun draw(canvas: Canvas) {
        canvas.drawRect(r, paint)
    }

    open fun gereBalle(b: Balle, dTime: Double): Boolean {
        var flag = false
        if (RectF.intersects(r, b.balle)) {
            if (b.balle.centerX() <= r.left || b.balle.centerX() >= r.right) {
                b.paroi_interract(false, dTime)
            }
            if (b.balle.centerY() >= r.bottom || b.balle.centerY() <= r.top) {
                b.paroi_interract(true, dTime)
            }
            flag = true
        }
        return flag
    }
}
