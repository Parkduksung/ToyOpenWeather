package com.example.toyopenweather.view.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.toyopenweather.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityContentFragmentTest {


    @Test
    fun show_init_display_test() {

        ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.et_input_city))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.button_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_city))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }


    @Test
    fun should_right_add_all_city_item_when_init_view() {
        ActivityScenario.launch(HomeActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.rv_city)).perform(
            RecyclerViewActions.scrollToPosition<CityViewHolder>(
                CITY_LIST_LAST_POSITION
            )
        )

        Espresso.onView(ViewMatchers.withText("Hyram"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun should_show_first_item_city_name_when_init_view() {
        ActivityScenario.launch(HomeActivity::class.java)

        Espresso.onView(ViewMatchers.withText("Hurzuf"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun should_not_show_last_item_city_name_when_not_scrolled_down() {
        ActivityScenario.launch(HomeActivity::class.java)

        Espresso.onView(ViewMatchers.withText("Hyram"))
            .check(ViewAssertions.doesNotExist())

    }


}