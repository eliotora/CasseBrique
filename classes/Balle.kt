package com.example.cassebrique

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*

class Balle (x1: Float, y1: Float, val diametre: Float) {
    val balle = RectF(x1 - diametre/2, y1 - diametre/2, x1 + diametre/2, y1 + diametre/2)
    val random = Random()
    val paint = Paint()
    var v = 400F
    var dir = PI/2
    var dead : Boolean = false
    var slowedT = 0.0

    init {
        paint.color = Color.WHITE
    }

    fun draw (canvas: Canvas) {
        canvas.drawOval(balle, paint)
    }

    fun paroi_interract(horizontale: Boolean, dTime: Double) {
        if (horizontale) {
            this.dir = -dir
        }
        else {
            this.dir = PI-dir
        }
        balle.offset((dTime*v*cos(dir)).toFloat(), (-dTime*v*sin(dir)).toFloat())
    }


    fun bouge(dTime: Double, lesParois: ArrayList<Parois>, laBarre: Barre, lesBriques: ArrayList<Brique>, deadzone: Float){
        if (slowedT > 0.0) {
            balle.offset(((1/1.5) * v*cos(dir) * dTime).toFloat(), (-(1/1.5)*v*sin(dir) * dTime).toFloat())
            slowedT -= dTime
        }
        else balle.offset((v*cos(dir)*dTime).toFloat(), (-v*sin(dir)*dTime).toFloat())
        if (balle.centerY() > deadzone) {
            this.meure()
        }
        else {
            laBarre.gereBalle(this, dTime)
            for (p in lesParois) {
                p.gereBalle(this, dTime)
            }
            for (b in lesBriques) {
                if (!b.dead) b.gereBalle(this, dTime)
            }

        }
    }

    fun barre_interract(new_dir:Double, dTime: Double) {
        dir = new_dir
        balle.offset((dTime*v*cos(dir)).toFloat(), (-dTime*v*sin(dir)).toFloat())
    }

    fun meure(){
        dead = true
    }

    fun slow() {
        slowedT = 10.0
    }
}
