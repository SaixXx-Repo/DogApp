package com.my.dogapp.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent

class DoubleTapListener(context: Context, private val onDoubleTap: () -> Unit) :
    GestureDetector.SimpleOnGestureListener() {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, this)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        onDoubleTap.invoke()
        return true
    }
}