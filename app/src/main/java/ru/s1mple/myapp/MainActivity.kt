package ru.s1mple.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), FilmsListClickListener, BackListener {

    private val moviesList = MoviesListFragment()
    private val movieDetails = MoviesDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesList.setListener(this@MainActivity)
        movieDetails.setListener(this@MainActivity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, moviesList)
                .commit()
        }
    }

    override fun onClick(film: Film) {
        movieDetails.arguments?.putInt("FILM_ID", film.filmId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, movieDetails)
            .commit()
    }

    override fun backToMain() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, moviesList)
            .commit()
    }
}