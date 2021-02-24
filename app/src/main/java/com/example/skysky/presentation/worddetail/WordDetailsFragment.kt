package com.example.skysky.presentation.worddetail

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.skysky.data.Word
import com.example.skysky.databinding.FragmentDetailBinding

private const val WORD = "word"

/**
 * Фрагмент детальной информации
 */
class WordDetailsFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private lateinit var word: Word
    private val mediaplayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding!!
        binding.lifecycleOwner = viewLifecycleOwner
        word = requireArguments().getParcelable(WORD)
            ?: throw IllegalArgumentException("Use newInstance")
        binding.word = word

        mediaplayer.setDataSource("https:" + word.meaning?.soundUrl.orEmpty())
        mediaplayer.prepareAsync()
        mediaplayer.setOnCompletionListener {
            binding.playButton.isPlaying = false
        }
        binding.playButton.setOnClickListener {
            mediaplayer.start()
            binding.playButton.isPlaying = true
        }
        binding.closeButton?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        binding = null
        mediaplayer.release()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(item: Word) =
            WordDetailsFragment().apply {
                arguments = bundleOf(WORD to item)
            }
    }
}