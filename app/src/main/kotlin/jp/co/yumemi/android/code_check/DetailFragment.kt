/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.DetailFragmentBinding

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val args: DetailFragmentArgs by navArgs()
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("検索した日時", lastSearchDate.toString())
        _binding = DetailFragmentBinding.bind(view)
        val selectedItem = args.searchResultContents

        binding?.let {
            it.ownerIconView.load(selectedItem.ownerIconUrl)
            it.nameView.text = selectedItem.name
            it.languageView.text = selectedItem.language
            it.starsView.text = "${selectedItem.stargazersCount} stars"
            it.watchersView.text = "${selectedItem.watchersCount} watchers"
            it.forksView.text = "${selectedItem.forksCount} forks"
            it.openIssuesView.text = "${selectedItem.openIssuesCount} open issues"
        }
    }
}
