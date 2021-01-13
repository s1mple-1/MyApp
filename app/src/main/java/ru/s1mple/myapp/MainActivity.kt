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

    override fun onClick(mId: Long) {
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFrame, MoviesDetailsFragment.newInstance(mId))
            .addToBackStack("${MoviesDetailsFragment::class.java}")
            .commit()
    }

    override fun backToMain() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}