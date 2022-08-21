/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.common.Resource
import jp.co.yumemi.android.code_check.common.State
import jp.co.yumemi.android.code_check.data.SearchResultsData
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val searchRepository: SearchRepository
) :
    AndroidViewModel(application) {

    private val _searchResponse = MediatorLiveData<Resource<SearchResultsData>>()
    val searchResponse: LiveData<Resource<SearchResultsData>> get() = _searchResponse

    init {
        _searchResponse.addSource(searchRepository.searchResponse) {
            when (it.state) {
                State.SUCCESS -> {
                    _searchResponse.value = it
                }
            }

        }
    }

    suspend fun getSearchResult(editText: String) {
        searchRepository.getSearchResult(editText)
    }
}