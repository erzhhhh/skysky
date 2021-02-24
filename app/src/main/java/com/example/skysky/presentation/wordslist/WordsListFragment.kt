package com.example.skysky.presentation.wordslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skysky.Application
import com.example.skysky.data.ScreenState
import com.example.skysky.data.Word
import com.example.skysky.databinding.FragmentWordsListBinding
import com.example.skysky.domain.SearchInteractor
import com.example.skysky.presentation.MainActivity
import com.example.skysky.presentation.OnItemClickListener
import javax.inject.Inject

/**
 * Фрагмент списка слов
 */
class WordsListFragment : Fragment() {

    @Inject
    lateinit var searchInteractor: SearchInteractor

    private lateinit var adapter: WordsRecyclerViewAdapter

    private val viewModel by viewModels<WordsListViewModel> {
        WordsListViewModelFactory(
            searchInteractor
        )
    }

    private var binding: FragmentWordsListBinding? = null

    override fun onAttach(context: Context) {
        (context.applicationContext as Application).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = binding!!

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.editText.requestFocus()

        adapter = WordsRecyclerViewAdapter(
            onItemClickListener = object : OnItemClickListener<Word> {
                override fun onItemClick(item: Word) {
                    if (!item.isExpandable) {
                        (activity as MainActivity).onItemClick(item)
                    } else {
                        viewModel.expand(item)
                    }
                }
            }
        )
        binding.recyclerView.adapter = adapter

        binding.editText.addTextChangedListener {
            it?.let {
                viewModel.search(it.toString())
            }
        }
        initObservers()
    }

    private fun initObservers() {
        val binding = binding!!
        viewModel.childModels.observe(
            viewLifecycleOwner,
            { adapter.submitList(it.takeIf(Collection<Word>::isNotEmpty)) })

        viewModel.screenState.observe(viewLifecycleOwner, {
            when (it.status) {
                ScreenState.Status.FAILED -> {
                    binding.recyclerView.visibility = GONE
                    binding.letsSearchContainer.visibility = GONE
                    binding.emptyResultContainer.visibility = GONE
                    binding.progressContainer.visibility = GONE
                    binding.failContainer.visibility = VISIBLE
                    binding.errorTextView.text = it.message
                }
                ScreenState.Status.RUNNING -> {
                    binding.recyclerView.visibility = VISIBLE
                    binding.letsSearchContainer.visibility = GONE
                    binding.emptyResultContainer.visibility = GONE
                    binding.progressContainer.visibility = VISIBLE
                    binding.failContainer.visibility = GONE
                }
                ScreenState.Status.SUCCESS_LOADED -> {
                    binding.recyclerView.visibility = VISIBLE
                    binding.letsSearchContainer.visibility = GONE
                    binding.emptyResultContainer.visibility = GONE
                    binding.progressContainer.visibility = GONE
                    binding.failContainer.visibility = GONE
                }
                ScreenState.Status.EMPTY_SEARCH -> {
                    binding.recyclerView.visibility = GONE
                    binding.letsSearchContainer.visibility = GONE
                    binding.emptyResultContainer.visibility = VISIBLE
                    binding.progressContainer.visibility = GONE
                    binding.failContainer.visibility = GONE
                }
                ScreenState.Status.NOT_SEARCH -> {
                    binding.recyclerView.visibility = GONE
                    binding.letsSearchContainer.visibility = VISIBLE
                    binding.emptyResultContainer.visibility = GONE
                    binding.progressContainer.visibility = GONE
                    binding.failContainer.visibility = GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

