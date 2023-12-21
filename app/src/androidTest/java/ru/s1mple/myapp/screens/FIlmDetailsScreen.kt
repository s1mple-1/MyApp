package ru.s1mple.myapp.screens

import android.view.View

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.s1mple.myapp.R

/**
 * Экран детальной информации о фильме
 */
object FIlmDetailsScreen : KScreen<FIlmDetailsScreen>() {
    override val layoutId: Int = R.layout.fragment_movies_details
    override val viewClass: Class<*> = ru.s1mple.myapp.details.MoviesDetailsFragment::class.java

    val backButton = KButton { withId(R.id.back_button) }
    val filmTitle = KTextView { withId(R.id.film_title_details) }
    val tagLine = KTextView { withId(R.id.tag_line_details) }
    val storyLineTitle = KTextView { withId(R.id.storyline_title) }
    val storyLineDesc = KTextView { withId(R.id.film_description) }
    val castTitle = KTextView { withId(R.id.cast) }

    /** Список актеров */
    val actorsRecycler = KRecyclerView(builder = { withId(R.id.actors_recycler) },
        itemTypeBuilder = { itemType(::ActorItem) })

    /** Айтем спика актеров */
    class ActorItem(parent: Matcher<View>) : KRecyclerItem<ActorItem>(parent)
}