package com.example.skysky.presentation.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.example.skysky.R

/**
 * Кастомная вью для отображения количества значений у слова
 */
class StackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var outLineColor = 0
    private var fillColor = 0
    private var outLinePaint = Paint()
    private var fillPaint = Paint()

    private var textPaint = Paint()

    private var desiredWidth = 0F
    private var desiredHeight = 0F
    private var tenPercent = 0F
    private var fourPercent = 0F

    private var bounds: Rect = Rect()

    var number = 0

    init {
        outLineColor = ResourcesCompat.getColor(
            resources,
            R.color.colorOutLineRectangle, null
        )
        fillColor = ResourcesCompat.getColor(
            resources,
            R.color.colorFillRectangle, null
        )

        outLinePaint.color = outLineColor
        outLinePaint.style = Paint.Style.STROKE
        outLinePaint.strokeWidth = 3F
        outLinePaint.flags = ANTI_ALIAS_FLAG

        fillPaint.color = fillColor
        fillPaint.flags = ANTI_ALIAS_FLAG
        fillPaint.setShadowLayer(12F, 0F, 0F, Color.GRAY)

        context.withStyledAttributes(attrs, R.styleable.StackView) {
            textPaint.textSize = getDimension(R.styleable.StackView_textSizForStack, 20F)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        tenPercent = (w / 10).toFloat()
        fourPercent = ((w * 4) / 100).toFloat()
        desiredWidth = width - (tenPercent * 2)
        desiredHeight = height - (tenPercent * 2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(tenPercent, tenPercent)
        canvas.drawRoundRect(
            0F,
            0F,
            desiredWidth,
            desiredHeight,
            6F,
            6F,
            fillPaint
        )
        canvas.drawRoundRect(
            0F,
            0F,
            desiredWidth,
            desiredHeight,
            6F,
            6F,
            outLinePaint
        )
        canvas.restore()

        canvas.save()
        canvas.translate(fourPercent, fourPercent)
        canvas.drawRoundRect(
            0F,
            0F,
            desiredWidth,
            desiredHeight,
            6F,
            6F,
            fillPaint
        )
        canvas.drawRoundRect(
            0F,
            0F,
            desiredWidth,
            desiredHeight,
            6F,
            6F,
            outLinePaint
        )

        val text = number.toString()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        val x = ((width - tenPercent) / 2) - bounds.centerX()
        val y = ((height - tenPercent) / 2) - bounds.centerY()
        canvas.drawText(
            text,
            x,
            y,
            textPaint
        )
        canvas.restore()
    }
}