package com.example.skysky.data

import android.os.Parcelable
import com.example.skysky.domain.Meaning
import kotlinx.android.parcel.Parcelize

/**
 * Сущность, объединяющая слово со значениями
 */
@Parcelize
data class Word(
    val id: String? = null,
    val word: String,
    val isExpandable: Boolean,
    val isExpanded: Boolean,
    val createdFromMeaning: Boolean,
    val meaning: Meaning? = null,
    val meanings: List<Meaning>? = null
) : Parcelable
