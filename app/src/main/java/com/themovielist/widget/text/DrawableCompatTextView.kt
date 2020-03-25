package com.themovielist.widget.text

import android.content.Context
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import com.themovielist.R

class DrawableCompatTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.DrawableCompatTextView)

            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            var drawableBottom: Drawable? = null
            var drawableTop: Drawable? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.DrawableCompatTextView_drawableLeftCompat)
                drawableRight = attributeArray.getDrawable(R.styleable.DrawableCompatTextView_drawableRightCompat)
                drawableBottom = attributeArray.getDrawable(R.styleable.DrawableCompatTextView_drawableBottomCompat)
                drawableTop = attributeArray.getDrawable(R.styleable.DrawableCompatTextView_drawableTopCompat)
            } else {
                val drawableLeftId = attributeArray.getResourceId(R.styleable.DrawableCompatTextView_drawableLeftCompat, -1)
                val drawableRightId = attributeArray.getResourceId(R.styleable.DrawableCompatTextView_drawableRightCompat, -1)
                val drawableBottomId = attributeArray.getResourceId(R.styleable.DrawableCompatTextView_drawableBottomCompat, -1)
                val drawableTopId = attributeArray.getResourceId(R.styleable.DrawableCompatTextView_drawableTopCompat, -1)

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId)
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId)
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId)
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId)
            }
            val defaultTextColor = ContextCompat.getColor(context, android.R.color.tab_indicator_text)

            setCompoundDrawablesWithIntrinsicBounds(applyDefaultColor(drawableLeft, defaultTextColor),
                    applyDefaultColor(drawableTop, defaultTextColor),
                    applyDefaultColor(drawableRight, defaultTextColor),
                    applyDefaultColor(drawableBottom, defaultTextColor))

            attributeArray.recycle()
        }
    }

    private fun applyDefaultColor(drawable: Drawable?, defaultTextColor: Int): Drawable? {
        drawable?.colorFilter = LightingColorFilter(defaultTextColor, defaultTextColor)
        return drawable
    }
}