package com.pointzi.library

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.pointzi.library.data.local.SharedPrefs
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomPointziTest {
    private lateinit var activityScenario: ActivityScenario<TestActivity>
    private val sharedPrefs by lazy { SharedPrefs }
    private val targetContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testLaunchTestActivityWithoutModel() {
        // launch out test activity
        activityScenario = ActivityScenario.launch(TestActivity::class.java)
        // check our dialog doesn't exist
        Espresso.onView(ViewMatchers.withId(R.id.layout_container))
            .check(ViewAssertions.doesNotExist())
        // check our floating action button doesn't exist
        Espresso.onView(ViewMatchers.withId(R.id.fab))
            .check(ViewAssertions.doesNotExist())

        activityScenario.close()
    }

    @Test
    fun testLaunchTestActivityWithDefaultModel() {
        // launch out test activity
        activityScenario = ActivityScenario.launch(TestActivity::class.java)
        // init the shared prefs
        // build our custom pointzi lib
        activityScenario.onActivity {
            sharedPrefs.init(it)
            it.buildCustomPointzi()
        }

        // check the fab is displayed and clickable
        Espresso.onView(ViewMatchers.withId(R.id.fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
            // perform click on the fab
            .perform(ViewActions.click())

        // check if the dialog layout is displayed after click on fab
        Espresso.onView(ViewMatchers.withId(R.id.dialog_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            // check if the background of the dialog is 10px
            .check(ViewAssertions.matches(withBackgroundRadius(10.0f)))

        // check if the icon is display and exist in the ui
        Espresso.onView(ViewMatchers.withId(R.id.iv_icon))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // check the text if displayed on the current time
        Espresso.onView(ViewMatchers.withId(R.id.tv_time))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.current_time)))

        // check the current time string is displayed and not eplty
        Espresso.onView(ViewMatchers.withId(R.id.tc_current_time))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(Matchers.not(withText(""))))

        // check the installedOn view if displayed and if the text machtes
        Espresso.onView(ViewMatchers.withId(R.id.tv_installed_on))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(
                ViewAssertions.matches(
                    // verify the text
                    withText(
                        String.format(
                            targetContext.getString(R.string.installed_on),
                            // get value from shared prefs instance
                            sharedPrefs.getInstalledOn()
                        )
                    )
                )
            )

        activityScenario.close()
    }

    @Test
    fun testLaunchTestActivityWithCustomModel() {

        activityScenario = ActivityScenario.launch(TestActivity::class.java)

        activityScenario.onActivity {
            sharedPrefs.init(it)
            sharedPrefs.setInstalledOn("20 May 2021")
            it.buildCustomPointzi()
        }

        // check the fab is displayed and clickable
        Espresso.onView(ViewMatchers.withId(R.id.fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
            .perform(ViewActions.click())

        // check installed on view
        Espresso.onView(ViewMatchers.withId(R.id.tv_installed_on))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(
                ViewAssertions.matches(
                    withText(
                        String.format(
                            targetContext.getString(R.string.installed_on),
                            "20 May 2021"
                        )
                    )
                )
            )

        activityScenario.close()
    }

    /**
     * custom matcher, to check the text view text with
     * the provided string
     */
    private fun withText(text: String): Matcher<View?> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
            }

            override fun matchesSafely(item: TextView?): Boolean {
                // return if not null and is the same
                return item != null && item.text == text
            }
        }
    }

    /**
     * custom matcher for the view, to check the background corner radius of the
     * current view with the provided radius
     */
    private fun withBackgroundRadius(radius: Float): Matcher<View?> {
        return object : BoundedMatcher<View?, View>(View::class.java) {
            override fun describeTo(description: Description) {
            }

            override fun matchesSafely(item: View?): Boolean {
                // return if not null and the corner raidus of background is equal radius
                return item != null && (item.background as GradientDrawable).cornerRadius == radius
            }

        }
    }

}