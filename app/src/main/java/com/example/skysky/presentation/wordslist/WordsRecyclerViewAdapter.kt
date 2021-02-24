package com.example.skysky.presentation.wordslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.skysky.data.Word
import com.example.skysky.databinding.WordItemLayoutBinding
import com.example.skysky.presentation.OnItemClickListener

/**
 * Адаптер для списка слов
 *
 * @param onItemClickListener колбэк нажатия на слово в списке
 */
class WordsRecyclerViewAdapter(
    var onItemClickListener: OnItemClickListener<Word>,
) : ListAdapter<Word, WordViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            WordItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val pm = getItem(position)
        holder.bind(pm)
    }
}