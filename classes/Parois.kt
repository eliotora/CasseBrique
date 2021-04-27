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

    open fun gereBalle(b: Balle): Boolean {
        var flag = false
        if (RectF.intersects(r, b.balle)) {
            if (b.balle.right >= r.left || b.balle.left <= r.right) {
                b.paroi_interract(false)
            }
            if (b.balle.top <= r.bottom || b.balle.bottom >= r.top) {
                b.paroi_interract(true)
            }
            flag = true
        }
        return flag
    }
}