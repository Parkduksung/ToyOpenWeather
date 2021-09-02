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
class HomeActivityTest {


    @Test
    fun should_show_container_when_init_display() {

        ActivityScenario.launch(HomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.container_home))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}