package com.example.toyopenweather.view.ui

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.toyopenweather.R
import com.example.toyopenweather.api.response.*
import org.junit.Test

class CityDetailFragmentTest {

    @Test
    fun show_init_display_test() {

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
    fun should_show_city_info_when_item_click() {


        launchFragmentInContainer<CityDetailFragment>(Bundle().apply {
            putInt("key_city_id", mockWeatherResponse.id)
        })

        val toWeatherItem = mockWeatherResponse.toWeatherItem()

        Espresso.onView(ViewMatchers.withText(toWeatherItem.name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(toWeatherItem.country))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(toWeatherItem.humidity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(toWeatherItem.weather))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText(toWeatherItem.celsius))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    companion object {
        val mockWeatherResponse = WeatherResponse(
            base = "stations",
            clouds = Clouds(all = 0),
            cod = 200,
            coord = Coord(lat = 44.55, lon = 34.2833),
            dt = 1630473046,
            id = 707860,
            main = Main(
                feels_like = 296.81,
                grnd_level = 1001,
                humidity = 56,
                pressure = 1013,
                sea_level = 1013,
                temp = 296.92,
                temp_max = 296.92,
                temp_min = 296.92
            ),
            name = "Gurzuf",
            sys = Sys(
                country = "UA",
                id = 2037874,
                sunrise = 1630465533,
                sunset = 1630513239,
                type = 2
            ),
            timezone = 10800,
            visibility = 10000,
            weather = listOf(
                Weather(
                    description = "clear sky",
                    icon = "01d",
                    id = 800,
                    main = "Clear"
                )
            ),
            wind = Wind(
                deg = 279,
                gust = 1.46,
                speed = 0.99
            )
        )
    }
}

