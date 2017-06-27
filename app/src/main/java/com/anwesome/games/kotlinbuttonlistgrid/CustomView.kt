package com.anwesome.games.kotlinbuttonlistgrid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

/**
 * Created by anweshmishra on 27/06/17.
 */
val paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
class CustomView(ctx:Context):View(ctx) {
    var time:Int = 0
    var btnGrid:ButtonGrid
    get():ButtonGrid {
        return btnGrid
    }
    set(btnGrid:ButtonGrid) {
        this.btnGrid = btnGrid
    }
    override fun onDraw(canvas:Canvas) {
        if(time == 0) {
            var w = canvas.width
            var h = canvas.height
            btnGrid = ButtonGrid(w*1.0f,h*1.0f,this)
        }
        time++
        btnGrid?.draw(canvas)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                btnGrid?.handleTap(event.x,event.y)
            }
        }
        return true
    }
}
class ButtonGrid {
    var btns:ArrayList<Button> = ArrayList<Button>()
    var animationHandler:AnimationHandler
    get():AnimationHandler {
        return animationHandler
    }
    set(animationHandler:AnimationHandler) {
        this.animationHandler = animationHandler
    }
    constructor(w:Float,h:Float,v:View) {
        init(w,h,9)
        animationHandler = AnimationHandler(v)

    }
    fun init(w:Float,h:Float,n:Int) {
        for(i in 0..9) {
            btns.add(Button(w,h,i))
        }
    }
    fun draw(canvas:Canvas) {
        btns.forEach {
            it.draw(canvas)
        }
        animationHandler?.animate()
    }
    fun handleTap(x:Float,y:Float) {
        btns.forEach {
            if(it.handleTap(x,y)) {
                animationHandler?.startAnimation(it)
            }
        }
    }
}
class Button {
    var x:Float = 0.0f
    var y:Float = 0.0f
    var size:Float = 0.0f
    var scale:Float = 0.0f
    var dir:Float = 0.0f
    var i:Int = 0
    constructor(w:Float,h:Float,i:Int) {
        this.size = w/6
        this.x = (i%3)*((w/3)+size/2)
        this.y = (i/3)*((h/3)+size/2)
        this.i = i

    }
    fun draw(canvas:Canvas) {
        canvas.save()
        canvas.translate(x,y)
        paint.color = Color.GRAY
        canvas.drawCircle(0.0f,0.0f,size/2,paint)
        canvas.save()
        canvas.scale(scale,scale)
        paint.color = Color.YELLOW
        canvas.drawCircle(0.0f,0.0f,size/2,paint)
        canvas.restore()
        canvas.restore()
    }
    fun handleTap(x:Float,y:Float):Boolean {
        var cond =  x>this.x-size/2 && x<this.x+size/2 && y>this.y-size/2 && y<this.y+size/2 && dir == 0.0f
        if(cond) {
            when(scale) {
                0.0f->{
                    dir = 1.0f
                }
                1.0f->{
                    dir = -1.0f
                }
            }
        }
        return cond
    }
    fun update() {
        scale += dir*0.2f
        if(scale > 1.0f) {
            scale = 0.0f
            dir = 0.0f
        }
        if(scale < 0.0f) {
            scale = 0.0f
            dir = 0.0f
        }
    }
    fun stopped():Boolean {
        return dir == 0.0f
    }
    fun startUpdating(dir:Float) {
        this.dir = dir
    }
    override fun hashCode():Int {
        return this.i
    }
}
data class AnimationHandler(val v:View) {
    var animated:Boolean = false
    var prev:Button
    get():Button {
        return prev
    }
    set(btn:Button) {
        prev = btn
    }
    var curr:Button
    get():Button {
        return curr
    }
    set(btn:Button) {
        curr = btn
    }
    fun animate() {
        if(animated) {
            curr?.update()
            prev?.update()
            if(curr?.stopped()) {
                animated = false
            }
            try {
                Thread.sleep(50)
                v.invalidate()
            } catch (ex:Exception) {

            }
        }
    }
    fun startAnimation(btn:Button) {
        if(!animated && curr == null) {
            curr = btn
            curr?.startUpdating(1.0f)
            prev?.startUpdating(-1.0f)
        }
    }

}