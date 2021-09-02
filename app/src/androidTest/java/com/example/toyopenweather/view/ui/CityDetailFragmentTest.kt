package com.example.toyopenweather.view.ui

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.toyopenweather.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityDetailFragmentTest {

    @Test
    fun show_init_display_test(): Unit = runBlocking {

        launchFragmentInContainer<CityDetailFragment>(Bundle().apply {
            putInt("key_city_id", MOCK_ID)
        })

        delay(1000)

        Espresso.onView(ViewMatchers.withText("Name"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Country"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Weather"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Celsius"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Humidity"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.button_back))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_weather))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_humidity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_country))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tv_celsius))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun should_show_city_info_when_item_click(): Unit = runBlocking {

        launchFragmentInContainer<CityDetailFragment>(Bundle().apply {
            putInt("key_city_id", MOCK_ID)
        })

        delay(1000)

        Espresso.onView(ViewMatchers.withId(R.id.tv_weather))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.withText(EMPTY_STRING))))

        Espresso.onView(ViewMatchers.withId(R.id.tv_humidity))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.withText(EMPTY_STRING))))

        Espresso.onView(ViewMatchers.withId(R.id.tv_name))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.withText(EMPTY_STRING))))

        Espresso.onView(ViewMatchers.withId(R.id.tv_country))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.withText(EMPTY_STRING))))

        Espresso.onView(ViewMatchers.withId(R.id.tv_celsius))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.withText(EMPTY_STRING))))

    }

    companion object {

        private const val MOCK_ID = 707860
        private const val EMPTY_STRING = ""


    }
}

