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
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.SearchResultContents
import jp.co.yumemi.android.code_check.databinding.SearchFragmentBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.searchResponse.observe(viewLifecycleOwner) {
            Log.d("searchResponse", "${it.data}")
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = SearchFragmentBinding.bind(view)
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: SearchResultContents) {
                toDetailPage(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                handleKeyEvent(editText)
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        kotlin.runCatching {
                            lifecycleScope.launch {
                                viewModel.getSearchResult(it)
                            }
                        }.onSuccess {
                            return@setOnEditorActionListener true
                        }
                    }
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    fun toDetailPage(item: SearchResultContents) {
        /*
        val action = SearchFragmentDirections
                    .actionToDetailPage(item)
                findNavController().navigate(action)
        */
    }

    private fun handleKeyEvent(view: View) {
        val inputMethodManager =
            view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

val diffUtil = object : DiffUtil.ItemCallback<SearchResultContents>() {
    override fun areItemsTheSame(
        oldItem: SearchResultContents,
        newItem: SearchResultContents
    ): Boolean {
        return oldItem.full_name == newItem.full_name
    }

    override fun areContentsTheSame(
        oldItem: SearchResultContents,
        newItem: SearchResultContents
    ): Boolean {
        return oldItem == newItem
    }

}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<SearchResultContents, CustomAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: SearchResultContents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.full_name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
