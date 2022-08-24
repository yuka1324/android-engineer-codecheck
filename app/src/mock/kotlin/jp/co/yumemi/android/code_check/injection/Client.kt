package jp.co.yumemi.android.code_check.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.service.MockSearchResultService
import jp.co.yumemi.android.code_check.service.SearchResultService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.mock.MockRetrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Client {
    private val httpBuilder: OkHttpClient.Builder
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/vnd.github.v3+json")
                    .method(original.method(), original.body())
                    .build()

                return@Interceptor chain.proceed(request)
            })
                .readTimeout(30, TimeUnit.SECONDS)

            return httpClient
        }

    @Singleton
    @Provides
    fun createService(): Retrofit {
        val client = httpBuilder.build()
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchResultService(
        retrofit: Retrofit,
        @ApplicationContext context: Context
    ): SearchResultService {
        val mockRetrofit = MockRetrofit.Builder(retrofit).build()
        val api = mockRetrofit.create(SearchResultService::class.java)
        return MockSearchResultService(api, context)
    }
}