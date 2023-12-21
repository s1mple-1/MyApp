package ru.s1mple.myapp.base

import android.app.Activity
import android.Manifest
import androidx.test.core.app.ActivityScenario
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import ru.s1mple.myapp.MainActivity


open class FilmsTestCase : TestCase() {

    fun lunchActivity(activityClass: Class<out Activity> = MainActivity::class.java) {
        ActivityScenario.launch(activityClass)
    }

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

}