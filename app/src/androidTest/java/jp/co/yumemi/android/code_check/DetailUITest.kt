import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.TopActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailUITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(TopActivity::class.java)

    @Test
    fun detailUITest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.searchInputText),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("a"), closeSoftKeyboard())

        sleep(10000)

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerView)
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val imageView = onView(
            allOf(
                withId(R.id.ownerIconView),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.nameView), withText("JetBrains/kotlin"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("JetBrains/kotlin")))

        val textView2 = onView(
            allOf(
                withId(R.id.languageView), withText("Written in Kotlin"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Written in Kotlin")))

        val textView3 = onView(
            allOf(
                withId(R.id.starsView), withText("42421 stars"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("42421 stars")))

        val textView4 = onView(
            allOf(
                withId(R.id.watchersView), withText("42421 watchers"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("42421 watchers")))

        val textView5 = onView(
            allOf(
                withId(R.id.forksView), withText("5232 forks"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("5232 forks")))

        val textView6 = onView(
            allOf(
                withId(R.id.openIssuesView), withText("152 open issues"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("152 open issues")))

        val textView7 = onView(
            allOf(
                withId(R.id.openIssuesView), withText("152 open issues"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("152 open issues")))
    }
}
