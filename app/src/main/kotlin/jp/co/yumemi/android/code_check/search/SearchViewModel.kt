/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.search

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.Resource
import jp.co.yumemi.android.code_check.data.SearchResultsData
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var lastSearchDate = MutableLiveData(Date())
    var editText: MutableLiveData<String> = MutableLiveData()
    var progressBarVisibility = MutableLiveData(View.GONE)
    var buttonVisibility = MutableLiveData(View.GONE)
    var noDataDialogVisibility = MutableLiveData(View.GONE)
    private val _searchResponse = MediatorLiveData<Resource<SearchResultsData>>()
    val searchResponse: LiveData<Resource<SearchResultsData>> get() = _searchResponse

    init {
        _searchResponse.addSource(searchRepository.searchResponse) {
            _searchResponse.value = it
        }
    }

    suspend fun getSearchResult() {
        lastSearchDate.value = Date()
        searchRepository.getSearchResult(editText.value, lastSearchDate.value)
    }
}