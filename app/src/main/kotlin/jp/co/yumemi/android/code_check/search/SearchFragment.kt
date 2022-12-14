/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.common.State
import jp.co.yumemi.android.code_check.common.showDialog
import jp.co.yumemi.android.code_check.data.SearchResultContents
import jp.co.yumemi.android.code_check.databinding.SearchFragmentBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private val adapter = SearchAdapter()
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.model = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.editText.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.getSearchResult()
            }
        }
        viewModel.searchResponse.observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> viewModel.progressBarVisibility.value = View.VISIBLE
                State.SUCCESS -> {
                    if (it.data?.items?.size == 0) {
                        viewModel.noDataDialogVisibility.value = View.VISIBLE
                    } else {
                        viewModel.noDataDialogVisibility.value = View.GONE
                    }
                    viewModel.progressBarVisibility.value = View.GONE
                    adapter.submitList(it.data?.items)
                }
                State.ERROR -> {
                    requireContext().showDialog()
                    viewModel.buttonVisibility.value = View.VISIBLE
                    viewModel.progressBarVisibility.value = View.GONE
                    Log.e("viewModel.searchResponse", "${it.message}")
                }
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun itemClick(item: SearchResultContents) {
                toDetailPage(item)
            }
        })

        binding.searchInputText.setOnEditorActionListener { editText, _, _ ->
            handleKeyEvent(editText)
            return@setOnEditorActionListener true
        }

        binding.searchContentsRecyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        binding.retryButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.buttonVisibility.value = View.GONE
                viewModel.getSearchResult()
            }
        }
    }

    fun toDetailPage(item: SearchResultContents) {
        val action = SearchFragmentDirections
            .actionToDetailPage(item)
        findNavController().navigate(action)
    }

    private fun handleKeyEvent(view: View) {
        val inputMethodManager =
            view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}