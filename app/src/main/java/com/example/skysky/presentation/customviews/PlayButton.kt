package com.example.skysky.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import com.example.skysky.R

/**
 * Кастомная вью для отображения кнопки воспроизведения аудио
 */
class PlayButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var states: IntArray?

    init {
        setImageDrawable(
            AppCompatResources.getDrawable(getContext(), R.drawable.ic_play_animation)
        )
        states = intArrayOf(-R.attr.state_playing)
    }

    var isPlaying = false
        set(value) {
            states!![0] = if (value) R.attr.state_playing else -R.attr.state_playing
            field = value
            refreshDrawableState()
        }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        @Suppress("SENSELESS_COMPARISON")
        if (states == null) {
            return super.onCreateDrawableState(extraSpace)
        }
        return mergeDrawableStates(super.onCreateDrawableState(extraSpace + states!!.size), states)
    }
}