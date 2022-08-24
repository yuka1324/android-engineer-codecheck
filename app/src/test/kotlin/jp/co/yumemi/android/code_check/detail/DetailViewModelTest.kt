package jp.co.yumemi.android.code_check.detail

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.mockk.MockKAnnotations
import io.mockk.mockk
import jp.co.yumemi.android.code_check.data.SearchResultContents
import jp.co.yumemi.android.code_check.data.SearchResultOwnerContents
import jp.co.yumemi.android.code_check.search.SearchRepository
import jp.co.yumemi.android.code_check.service.SearchResultService
import kotlinx.coroutines.DelicateCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

class TestArchTaskExecutor : TaskExecutor() {
    override fun executeOnDiskIO(runnable: Runnable) {
        runnable.run()
    }

    override fun isMainThread(): Boolean {
        return true
    }

    override fun postToMainThread(runnable: Runnable) {
        runnable.run()
    }
}

@RunWith(JUnitPlatform::class)
@DelicateCoroutinesApi
class DetailViewModelTest : Spek({
    describe("DetailViewModelTest") {
        lateinit var sut: DetailViewModel
        lateinit var searchRepository: SearchRepository
        val searchResultService = mockk<SearchResultService>()
        MockKAnnotations.init(this, relaxUnitFun = true)
        searchRepository = SearchRepository(searchResultService)
        sut = DetailViewModel(searchRepository)

        beforeEachTest {
            ArchTaskExecutor.getInstance().setDelegate(TestArchTaskExecutor())
        }

        afterEachTest {
            ArchTaskExecutor.getInstance().setDelegate(null)
        }

        context("setView function test start") {
            it("param compare start") {
                sut.setView(
                    item = SearchResultContents(
                        full_name = "test_name",
                        owner = SearchResultOwnerContents(
                            avatar_url = ""
                        ),
                        language = "test",
                        stargazers_count = 1,
                        watchers_count = 100,
                        forks_count = 200,
                        open_issues_count = 50
                    )
                )

                sut.nameViewText.value shouldBeEqualTo "test_name"
                sut.languageViewText.value shouldBeEqualTo "test"
                sut.starsViewText.value shouldBeEqualTo "1"
                sut.watchersViewText.value shouldBeEqualTo "100"
                sut.forksViewText.value shouldBeEqualTo "200"
                sut.openIssuesViewText.value shouldBeEqualTo "50"
            }
        }
    }

})