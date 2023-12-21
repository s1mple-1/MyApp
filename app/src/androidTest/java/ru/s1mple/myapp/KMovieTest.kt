package ru.s1mple.myapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.View
import androidx.test.espresso.screenshot.captureToBitmap
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import androidx.test.runner.screenshot.Screenshot
import io.github.kakaocup.kakao.delegate.ViewInteractionDelegate
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.s1mple.myapp.base.FilmsTestCase
import ru.s1mple.myapp.screens.FIlmDetailsScreen
import ru.s1mple.myapp.screens.MainScreen
import ru.s1mple.myapp.screenshots.ImageComparison
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * MEPT-1001 - EP - Экран детальной информации о фильме
 * ссылка на ТК в JIRA
 *
 * @author Юрманов Роман
 */
class KMovieTest : FilmsTestCase() {


    @Test
    fun test() {
        before {
            lunchActivity()
        }.after {
        }.run {
            step("Нажать на карточку фильма с заголовком Mortal Kombat") {

                MainScreen {
                    val actual = view.interaction.captureToBitmap()
                    val expected = BitmapFactory.decodeStream(device.context.assets.open("main_menu_expected.png"))
                    val destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "screenshots")
                    ImageComparison(expected, actual, destination).compareImages()




                    assertTrue(actual.sameAs(expected))



//                    clickOnFilm("Mortal Kombat")


                }
                step("Открылся экран детальной информации о фильме") {
//                    FIlmDetailsScreen {
//                        filmTitle.hasText("Mortal Kombat")
//                    }
                }
            }
            step("Нажать кнопку назад") {
//                FIlmDetailsScreen.backButton.click()
//                step("Открылся экран со списком фильмов") {
//                    MainScreen {
//                        filmsRecycler.isDisplayed()
//                    }
//                }
            }
        }
    }

    private fun processImage(bitMap: Bitmap, screenName: String) {
        val imagePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "screenshots")
        imagePath.mkdirs()
        val image = File(imagePath, "$screenName.png")
        val bf = FileOutputStream(image)
        bf.use {
            bitMap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

    }

    private fun getViewScreenShot(delegate: ViewInteractionDelegate, sreenshotName: String) {
        delegate.check {view, noFoundViewException ->
            if (view is View) {
                val capture = Screenshot.capture(view)
                capture.format = Bitmap.CompressFormat.PNG
                capture.name = "$sreenshotName legacy"
                BasicScreenCaptureProcessor().process(capture)
            } else {
                throw noFoundViewException
            }
        }
    }
}