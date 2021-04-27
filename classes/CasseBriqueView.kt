package com.example.cassebrique

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.MotionEvent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.util.*
import kotlin.collections.ArrayList


class CasseBriqueView @JvmOverloads constructor (context: Context?, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback,Runnable {
    val backgroundPaint = Paint()
    val lifePaint = Paint()
    val deadPaint = Paint()
    lateinit var thread: Thread
    val random = Random()
    var drawing = false
    lateinit var canvas: Canvas
    var screenWidth = 0f
    var screenHeight = 0f
    lateinit var barre: Barre
    lateinit var balle: Balle
    lateinit var parois: ArrayList<Parois>
    var briques = ArrayList<Brique>()
    val activity = context as FragmentActivity
    var text: String = " "
    var life = 3
    var deadzone : Float = 0f

    init {
        backgroundPaint.color = Color.BLACK
        lifePaint.setColor(Color.WHITE)
        deadPaint.setColor(Color.MAGENTA)
        for (i in 1..3) {
            for (j in 1..5) {
                briques.add(Brique(0f + j * 1f, 0f + i * 1f, 0f + (j + 1) * 1f, 0f + (i + 1) * 1f))
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        val x1 = -screenWidth / 12
        val y1 = 2 * screenHeight / 10
        val a = screenWidth / 6
        val b = screenHeight / 20
        barre = Barre(screenWidth / 2, 6 * screenHeight / 8, screenWidth / 5)
        balle = Balle(screenWidth / 2, 23 * screenHeight / 32, 35f)
        parois = arrayListOf(
                Parois(5f, 5f, 25f, screenHeight),
                Parois(5f, 5f, screenWidth - 25f, 25f),
                Parois(screenWidth - 25f, 5f, screenWidth, screenHeight)
        )
        for (i in 1..3) {
            for (j in 1..5) {
                briques[(i-1)*5+(j-1)].resize(RectF(x1 + j * a, y1 + i * b, x1 + (j + 1) * a, y1 + (i + 1) * b))
            //briques.add(Brique(x1 + j * a, y1 + i * b, x1 + (j + 1) * a, y1 + (i + 1) * b))
            }
        }
        deadzone = 13*screenHeight/16
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun run() {
        while (drawing) {
            draw()
        }
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat(), backgroundPaint)
            barre.draw(canvas)
            balle.draw(canvas)
            for (p in parois) {
                p.draw(canvas)
            }
            for (b in briques) {
                b.draw(canvas)
            }
            val e = 20f
            val r = 12f
            for (i in 1..life) {
                val rect = RectF(screenWidth/2+ (i-2)*(2*r+e) - r, 14*screenHeight/16 - r,
                        screenWidth/2 + (i-2)*(2*r+e) + r,
                        14*screenHeight/16 + r)
                canvas.drawOval(rect, lifePaint)
            }
            //canvas.drawRect(0f, deadzone, screenWidth, deadzone + 50f, deadPaint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        barre.move(e)
        return true
    }

    fun showMenu(messageId: Int) {
        class Menu : DialogFragment() {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString(messageId))
                builder.setMessage("Tu veux recommencer?")
                builder.setPositiveButton("Recommencer", DialogInterface.OnClickListener { _, _ -> newGame() })
                builder.setNegativeButton("Continuer", DialogInterface.OnClickListener { _, _ -> resume()})
                return builder.create()
            }


        }
        activity.runOnUiThread(
                Runnable {
                    val ft = activity.supportFragmentManager.beginTransaction()
                    val prev = activity.supportFragmentManager.findFragmentByTag("dialog")
                    if (prev != null) {
                        ft.remove(prev)
                    }
                    ft.addToBackStack(null)
                    val menu = Menu()
                    menu.setCancelable(false)
                    menu.show(ft, "dialog")
                }
        )
    }

    fun newGame() {
        for (b in briques) {
            b.resistance = 3
            b.dead = false
        }
        life = 3
        resume()
    }
}