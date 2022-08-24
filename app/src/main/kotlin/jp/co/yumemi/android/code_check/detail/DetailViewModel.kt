package jp.co.yumemi.android.code_check.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.SearchResultContents
import jp.co.yumemi.android.code_check.search.SearchRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import javax.inject.Inject


@DelicateCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _lastSearchDate = MediatorLiveData<Date?>()
    val lastSearchDate: LiveData<Date?> get() = _lastSearchDate

    init {
        _lastSearchDate.addSource(searchRepository.lastSearchDate) {
            _lastSearchDate.value = it
        }
    }

    var nameViewText = MutableLiveData("")
    var languageViewText = MutableLiveData("")
    var starsViewText = MutableLiveData("")
    var forksViewText = MutableLiveData("")
    var watchersViewText = MutableLiveData("")
    var openIssuesViewText = MutableLiveData("")

    fun setView(item: SearchResultContents) {
        nameViewText.value = item.full_name
        languageViewText.value = item.language
        starsViewText.value = item.stargazers_count.toString()
        forksViewText.value = item.forks_count.toString()
        watchersViewText.value = item.watchers_count.toString()
        openIssuesViewText.value = item.open_issues_count.toString()
    }

    fun setLastSearchDate() {
        searchRepository.setLastSearchDate()
    }
}
