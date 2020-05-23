package com.example.kotlindemo.canvas_sign_pad

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.kotlindemo.R

/*
https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-canvas/
 */
class SignaturePadView(context: Context) : View(context) {

    private var list: MutableList<Array<Float>> = mutableListOf()
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var paint: Paint
    private var initialFloatArray: Array<Float>? = null
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.white_color, null)
    private var motionTouchX: Float = 0f
    private var motionTouchY: Float = 0f
    private var path = Path()

    private var currentX = 0f
    private var currentY = 0f

    /*
    Using a path, there is no need to draw every pixel and each time request a refresh of the display.
    Instead, you can (and will) interpolate a path between points for much better performance.

    If the finger has barely moved, there is no need to draw.
    If the finger has moved less than the touchTolerance distance, don't draw.
    scaledTouchSlop returns the distance in pixels a touch can wander before the system thinks the user is scrolling.
     */
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchX = event.x
        motionTouchY = event.y

        println("onTouchEvent " + event.action)


        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (::bitmap.isInitialized) bitmap.recycle()

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)

        paint = Paint()
        paint.isAntiAlias = true
        paint.color = resources.getColor(R.color.green_color)
        paint.isDither = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 8.0f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchX, motionTouchY)
        currentX = motionTouchX
        currentY = motionTouchY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchX - currentX)
        val dy = Math.abs(motionTouchY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(currentX, currentY, (motionTouchX + currentX) / 2, (motionTouchY + currentY) / 2)
            currentX = motionTouchX
            currentY = motionTouchY
            // Draw the path in the extra bitmap to cache it.
            canvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUp() {
        // Reset the path so it doesn't get drawn again.
        path.reset()
    }

    private fun drawLine(motionTouchX: Float, motionTouchY: Float) {
        if (initialFloatArray == null) {
            initialFloatArray = arrayOf(motionTouchX, motionTouchY)
            return
        }

        canvas.drawLine(
            initialFloatArray!!.get(0),
            initialFloatArray!!.get(1),
            motionTouchX,
            motionTouchY,
            paint
        )
        list.add(arrayOf(motionTouchX, motionTouchY))
        initialFloatArray = arrayOf(motionTouchX, motionTouchY)

        invalidate()
    }


}