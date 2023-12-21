package ru.s1mple.myapp.screens

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.s1mple.myapp.R

/**
 * Главный экран приложения со списком фильмов
 *
 * @author Юрманов Роман
 */
object MainScreen : KScreen<MainScreen>() {
    override val layoutId: Int = R.id.films_recycler
    override val viewClass: Class<*> = ru.s1mple.myapp.movies.MoviesListFragment::class.java

    val filmsRecycler = KRecyclerView(builder = { withId(R.id.films_recycler) }, itemTypeBuilder = {
        itemType(::FilmItem)
    })


    val topMenu = KView { withId(R.id.top_menu) }

    class FilmItem(parent: Matcher<View>) : KRecyclerItem<FilmItem>(parent) {
        val tagLine = KTextView(parent) { withId(R.id.tag_line_details) }
        val filmTitle = KTextView(parent) { withId(R.id.film_title_main) }
    }

    /**
     * Нажать на карточку с фильмом [FilmItem] в списке [filmsRecycler]
     *
     * @param filmName название фильма
     */
    fun clickOnFilm(filmName: String) {
        filmsRecycler.childWith<MainScreen.FilmItem> {
            withDescendant { withText(filmName) }
        } perform {
            click()
        }
    }
}