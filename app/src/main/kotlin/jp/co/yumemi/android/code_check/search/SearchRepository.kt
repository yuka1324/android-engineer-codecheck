package jp.co.yumemi.android.code_check.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import jp.co.yumemi.android.code_check.common.Resource
import jp.co.yumemi.android.code_check.data.SearchResultsData
import jp.co.yumemi.android.code_check.service.SearchResultService
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
class SearchRepository @Inject constructor(
    private var searchResultService: SearchResultService
) {
    private val _searchResponse = MediatorLiveData<Resource<SearchResultsData>>()
    val searchResponse: LiveData<Resource<SearchResultsData>> get() = _searchResponse

    suspend fun getSearchResult(inputEditText: String) {
        _searchResponse.value = Resource.loading()
        if (searchResultService.getSearchResult(inputEditText).isSuccessful) {
            _searchResponse.value =
                Resource.success(searchResultService.getSearchResult(inputEditText).body())
        } else {
            _searchResponse.value = Resource.error("getSearchResult is not successful", null)
        }
    }
}