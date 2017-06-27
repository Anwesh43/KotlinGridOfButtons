package com.anwesome.games.kotlinbuttonlistgrid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(),OnSelectionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customView = CustomView(this)
        customView.onSelectionListener = this
        setContentView(customView)
    }

    override fun onDeSelect(index: Int?) {
        Toast.makeText(this,"DeSelected $index",Toast.LENGTH_SHORT).show()
    }

    override fun onSelect(index: Int?) {
        Toast.makeText(this,"Selected $index",Toast.LENGTH_SHORT).show()
    }
}
