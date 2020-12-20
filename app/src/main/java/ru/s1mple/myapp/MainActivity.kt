package ru.s1mple.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.s1mple.myapp.details.MoviesDetailsFragment
import ru.s1mple.myapp.movies.MoviesListFragment

class MainActivity : AppCompatActivity(),
    MoviesListFragment.FilmClickListener,
    MoviesDetailsFragment.BackListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, MoviesListFragment.newInstance())
                .commit()
        }
    }

    override fun onClick(filmId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, MoviesDetailsFragment.newInstance(filmId))
            .commit()
    }

    override fun backToMain() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, MoviesListFragment.newInstance())
            .commit()
    }
}