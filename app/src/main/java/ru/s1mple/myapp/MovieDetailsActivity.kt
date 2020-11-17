package ru.s1mple.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val view : View = findViewById(R.id.back_button)
        view.setOnClickListener { backToMain() }
    }

    private fun backToMain() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}