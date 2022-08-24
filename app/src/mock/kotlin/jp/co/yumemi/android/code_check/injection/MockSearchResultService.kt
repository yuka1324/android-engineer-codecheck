package jp.co.yumemi.android.code_check.service

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jp.co.yumemi.android.code_check.data.SearchResultsData
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate

class MockSearchResultService(
    private val delegate: BehaviorDelegate<SearchResultService>,
    private var context: Context
) :
    SearchResultService {

    override suspend fun getSearchResult(q: String): Response<SearchResultsData> {
        kotlin.runCatching {
            val assetManager = context.resources.assets
            val inputStream = assetManager?.open("json/github_result.json")
            val json = inputStream?.bufferedReader()?.use { it.readText() }
            Moshi.Builder().add(KotlinJsonAdapterFactory())
                .build().adapter(SearchResultsData::class.java)
                .fromJson(json ?: "")
        }.onSuccess {
            return delegate.returningResponse(it).getSearchResult(q)
        }.onFailure {
            Log.e("MockServiceError", "$it")
        }
        return delegate.returningResponse("").getSearchResult(q)
    }
}