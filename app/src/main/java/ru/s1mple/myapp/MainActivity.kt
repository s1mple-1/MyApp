package ru.s1mple.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view : ImageButton = findViewById(R.id.first_film)
        view.setOnClickListener { nextScreen() }
    }

    private fun nextScreen() {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(intent)
    }
}