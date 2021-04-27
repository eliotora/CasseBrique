package com.example.cassebrique

import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var drawingView: CasseBriqueView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById<CasseBriqueView>(R.id.vMain)
    }

    fun onClick(v: View) {
        if (drawingView.drawing) drawingView.pause()
        drawingView.showMenu(R.string.menu)
    }

    override fun onPause() {
        super.onPause()
        drawingView.pause()
    }

    override fun onResume() {
        super.onResume()
        drawingView.resume()
    }
}