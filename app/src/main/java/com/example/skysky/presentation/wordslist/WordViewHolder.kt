package com.example.skysky.presentation.wordslist

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.skysky.R
import com.example.skysky.data.Word
import com.example.skysky.databinding.WordItemLayoutBinding
import com.example.skysky.domain.Meaning
import com.example.skysky.presentation.OnItemClickListener

/**
 * Вьюхолдер для слова в списке
 *
 * @param binding биндинг слова в списке
 * @param onItemClickListener колбэк нажатия на слово в списке
 */
class WordViewHolder(
    private val binding: WordItemLayoutBinding,
    private val onItemClickListener: OnItemClickListener<Word>
) :
    RecyclerView.ViewHolder(binding.root) {

    var expanded = false

    fun bind(word: Word) {
        setOriginalWordVisibility(word)
        when {
            word.isExpandable -> {
                binding.root.setBackgroundResource(0)
                setExpandableState(word)
            }
            word.createdFromMeaning -> {
                binding.root.setBackgroundResource(R.drawable.expanded_word_background)
                setNotExpandedState(word)
            }
            else -> {
                binding.root.setBackgroundResource(0)
                setNotExpandedState(word)
            }
        }
    }

    private fun setOriginalWordVisibility(word: Word) {
        if (word.word.isEmpty()) {
            binding.original.visibility = View.GONE
        } else {
            binding.original.text = word.word
        }
    }

    private fun setExpandableState(word: Word) {
        binding.imageView.visibility = View.GONE
        binding.stack.visibility = View.VISIBLE
        binding.button.visibility = View.VISIBLE
        binding.stack.number = word.meanings?.size ?: 0
        binding.translation.text = getMeaningsInRow(word.meanings)
        expandableClickListener(word)
    }

    private fun setNotExpandedState(word: Word) {
        binding.imageView.visibility = View.VISIBLE
        binding.stack.visibility = View.GONE
        binding.button.visibility = View.GONE
        binding.translation.text = word.meaning?.translation?.text.orEmpty()
        Glide
            .with(binding.imageView.context)
            .load("https:" + word.meaning?.previewUrl.orEmpty())
            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
            .into(binding.imageView)
            .waitForLayout()

        binding.root.setOnClickListener {
            onItemClickListener.onItemClick(word)
        }
    }

    private fun getMeaningsInRow(meanings: List<Meaning>?): String {
        val stringBuilder = StringBuilder()
        meanings?.forEachIndexed { index, meaning ->
            stringBuilder.append(meaning.translation.text)
            if (index != meanings.lastIndex) {
                stringBuilder.append(", ")
            }
        }
        return stringBuilder.toString()
    }

    private fun expandableClickListener(word: Word) {

        binding.root.setOnClickListener {
            val shake: Animation =
                AnimationUtils.loadAnimation(binding.root.context, R.anim.shake)
            binding.stack.startAnimation(shake)

            val rotate: Animation = if (expanded) {
                AnimationUtils.loadAnimation(binding.root.context, R.anim.rotate_up)
            } else {
                AnimationUtils.loadAnimation(binding.root.context, R.anim.rotate_down)
            }
            binding.button.startAnimation(rotate)
            expanded = !expanded

            if (expanded) {
                binding.root.setBackgroundResource(R.drawable.expanded_word_background)
            } else {
                binding.root.setBackgroundResource(0)
            }

            onItemClickListener.onItemClick(word)
        }
    }
}