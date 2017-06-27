package com.anwesome.games.kotlinbuttonlistgrid

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View

/**
 * Created by anweshmishra on 27/06/17.
 */
class CustomView(ctx:Context):View(ctx) {
    override fun onDraw(canvas:Canvas) {
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}