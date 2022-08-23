package jp.co.yumemi.android.code_check


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class TopActivityUITest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(TopActivity::class.java)

    @Test
    fun topActivityTest() {
        val textView = onView(
            allOf(
                withText("Android Engineer CodeCheck"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Android Engineer CodeCheck")))

        val editText = onView(
            allOf(
                withId(R.id.searchInputText), withHint("GitHub のリポジトリを検索できるよー"),
                withParent(withParent(withId(R.id.searchInputLayout))),
                isDisplayed()
            )
        )
        editText.check(matches(withHint("GitHub のリポジトリを検索できるよー")))
    }
}
