package jp.co.yumemi.android.code_check.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import jp.co.yumemi.android.code_check.common.Resource
import jp.co.yumemi.android.code_check.data.SearchResultsData
import jp.co.yumemi.android.code_check.service.SearchResultService
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@DelicateCoroutinesApi
class SearchRepository @Inject constructor(
    private var searchResultService: SearchResultService
) {
    private val savedLastSearchDate = MutableLiveData<Date?>(null)
    private val _lastSearchDate = MediatorLiveData<Date?>()
    val lastSearchDate: LiveData<Date?> get() = _lastSearchDate
    private val _searchResponse = MediatorLiveData<Resource<SearchResultsData>>()
    val searchResponse: LiveData<Resource<SearchResultsData>> get() = _searchResponse

    suspend fun getSearchResult(inputEditText: String?, lastSearchDate: Date?) {
        _searchResponse.value = Resource.loading()
        if (searchResultService.getSearchResult(inputEditText).isSuccessful) {
            savedLastSearchDate.value = lastSearchDate
            _searchResponse.value =
                Resource.success(searchResultService.getSearchResult(inputEditText).body())
        } else {
            _searchResponse.value = Resource.error(
                "${searchResultService.getSearchResult(inputEditText).errorBody()?.string()}",
                null
            )
        }
    }

    fun setLastSearchDate() {
        _lastSearchDate.value = savedLastSearchDate.value
    }
}