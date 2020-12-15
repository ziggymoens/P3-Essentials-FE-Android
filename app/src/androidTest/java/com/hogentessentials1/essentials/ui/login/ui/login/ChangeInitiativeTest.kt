package com.hogentessentials1.essentials.ui.login.ui.login

import androidx.test.espresso.Espresso.onData
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.hogentessentials1.essentials.R
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test

/**
 * @author Jonathan Vanden Eynden
 */


@RunWith(AndroidJUnit4ClassRunner::class)
class ChangeInitiativeTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun test_changeInitiative_navigation() {
        onView(withId(R.id.username)).perform(clearText()) // TODO weghalen nadat username niet meer automatisch is ingevuld
        onView(withId(R.id.username)).perform(typeText("ziggy@hogent.com"))
        onView(withId(R.id.password)).perform(typeText("P@ssword1"))
        onView(withId(R.id.login)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.changes)).perform(click())
        onView(withId(R.id.ci_list )).check(matches(isDisplayed()))
    }
}