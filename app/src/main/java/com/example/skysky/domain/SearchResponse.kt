package com.example.skysky.domain

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Entity, получаемая с бэка
 */
@Parcelize
data class WordData(
    @Expose
    @SerializedName("id")
    val id: String? = null,

    @Expose
    @SerializedName("text")
    val text: String? = null,

    @Expose
    @SerializedName("meanings")
    val meanings: List<Meaning>,
) : Parcelable

@Parcelize
data class Meaning(

    var text: String,

    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("translation")
    val translation: Translation,

    @Expose
    @SerializedName("previewUrl")
    val previewUrl: String,

    @Expose
    @SerializedName("imageUrl")
    val imageUrl: String,

    @Expose
    @SerializedName("transcription")
    val transcription: String,

    @Expose
    @SerializedName("soundUrl")
    val soundUrl: String
) : Parcelable

@Parcelize
data class Translation(
    @Expose
    @SerializedName("text")
    val text: String
) : Parcelable