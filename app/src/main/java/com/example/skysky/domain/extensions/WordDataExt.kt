package com.example.skysky.domain.extensions

import com.example.skysky.data.Word
import com.example.skysky.domain.WordData

/**
 * Маппинг entity объекта в сущность, неободходимую presentation слою
 */
fun List<WordData>.mapWordsLIst(): List<Word> {
    return this.map { wordData ->
        val isExpandable = wordData.meanings.size > 1
        Word(
            id = wordData.id.orEmpty(),
            word = wordData.text.orEmpty(),
            isExpandable = isExpandable,
            isExpanded = false,
            createdFromMeaning = false,
            meaning = if (isExpandable) null else wordData.meanings.first()
                .copy(text = wordData.text.orEmpty()),
            meanings = if (isExpandable) wordData.meanings.apply {
                this.map {
                    it.text = wordData.text.orEmpty()
                }
            } else null
        )
    }
}
