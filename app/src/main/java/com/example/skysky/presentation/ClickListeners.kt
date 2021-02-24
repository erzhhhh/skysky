package com.example.skysky.presentation

/**
 * Колбэк нажатия на слово в списке
 */
interface OnItemClickListener<Item> {
    fun onItemClick(item: Item)
}