package com.example.toyopenweather.view.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.toyopenweather.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityMainFragmentTest {


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

}