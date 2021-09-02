package com.example.toyopenweather.view.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.toyopenweather.R
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.CityList
import com.example.toyopenweather.util.CityImpl
import com.example.toyopenweather.util.Result
import com.example.toyopenweather.view.viewholder.CityViewHolder
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class CityContentFragmentTest {


    @Test
    fun show_init_display_test() {

        launchFragmentInContainer<CityContentFragment>()

        Espresso.onView(ViewMatchers.withId(R.id.et_input_city))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.button_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.rv_city))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }


    @Test
    fun should_right_add_all_city_item_when_init_view(): Unit = runBlocking {

        launchFragmentInContainer<CityContentFragment>()

        delay(500)

        Espresso.onView(ViewMatchers.withId(R.id.rv_city)).perform(
            RecyclerViewActions.scrollToPosition<CityViewHolder>(
                CITY_LIST_LAST_POSITION
            )
        )

        Espresso.onView(ViewMatchers.withText(lastIndexItem.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun should_show_first_item_city_name_when_init_view(): Unit = runBlocking {

        launchFragmentInContainer<CityContentFragment>()

        sleep(500)

        Espresso.onView(ViewMatchers.withText(firstIndexItem.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun should_not_show_last_item_city_name_when_not_scrolled_down(): Unit = runBlocking {

        launchFragmentInContainer<CityContentFragment>()

        delay(500)

        Espresso.onView(ViewMatchers.withText(lastIndexItem.name))
            .check(ViewAssertions.doesNotExist())

    }

    companion object {

        private val getTotalCityList: CityList =
            (CityImpl(InstrumentationRegistry.getInstrumentation().targetContext).getCityList() as Result.Success).value

        private val lastIndexItem: CityItem
            get() = getTotalCityList.last()

        private val firstIndexItem: CityItem
            get() = getTotalCityList.first()

        private val CITY_LIST_LAST_POSITION: Int
            get() = getTotalCityList.lastIndex
    }


}