package jp.co.yumemi.android.code_check


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jp.co.yumemi.android.code_check.search.SearchAdapter
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchUITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(TopActivity::class.java)

    @Test
    fun searchViewUITest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.searchInputText),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("a"), closeSoftKeyboard())

        sleep(10000)

        onView(withId(R.id.recyclerView)).check(matches(hasText(0, "JetBrains/kotlin")))
        onView(withId(R.id.recyclerView)).check(matches(hasText(1, "hussien89aa/KotlinUdemy")))
    }

    @Test
    fun searchBarMaxLengthTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.searchInputText),
                isDisplayed()
            )
        )
        textInputEditText.perform(
            replaceText("hogehogehogehogehogehogehogehogehogehogehogehogehogehogehoge"),
            closeSoftKeyboard()
        )
        textInputEditText.check(matches(withText("hogehogehogehogehoge")))

    }

    private fun hasText(position: Int, text: String) = object : TypeSafeMatcher<View>() {
        var innerText: String? = null

        override fun describeTo(description: Description) {
            description.appendText(String.format(text + "is not equal to %s.", text))
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is RecyclerView) return false

            val holder = view.findViewHolderForAdapterPosition(position) as SearchAdapter.ViewHolder
            innerText =
                (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text.toString()
            return innerText == text
        }
    }
}
