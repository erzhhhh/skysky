package com.example.skysky.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.skysky.R

private const val HTTPS_PREFIX = "https:"

/**
 * Адаптер для загрузки изображения
 *
 * @param imageUrl адрес ссылки изображения
 */
@BindingAdapter(
    value = ["imageUrl"]
)
fun setImage(
    imageView: ImageView,
    imageUrl: String?
) {
    imageUrl?.let {
        Glide
            .with(imageView.context)
            .load(HTTPS_PREFIX + it)
            .into(imageView)
            .waitForLayout()
    } ?: run {
        imageView.setImageResource(R.drawable.ic_box)
    }
}
