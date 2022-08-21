package jp.co.yumemi.android.code_check.service

import jp.co.yumemi.android.code_check.data.SearchResultsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchResultService {

    @GET("search/repositories")
    suspend fun getSearchResult(
        @Query("q") q: String
    ): Response<SearchResultsData>

}