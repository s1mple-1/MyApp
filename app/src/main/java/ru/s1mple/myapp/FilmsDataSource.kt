package ru.s1mple.myapp

/**
 * Класс с данными фильмов
 */
class FilmsDataSource {
    fun getFilms(): List<Film> {
        return listOf(
            Film(
                0,
                R.drawable.avengers_main,
                R.drawable.avengers_header_image,
                "13+",
                "Avengers: End Game",
                "137 MIN",
                "123 REVIEWS",
                "Action, Adventure, Drama",
                false,
                4,
                "Avengers:\nEnd Game",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe."
            ),
            Film(
                1,
                R.drawable.tenet_main,
                R.drawable.avengers_header_image,
                "16+",
                "Tenet",
                "97 MIN",
                "98 REVIEWS",
                "Action, Sci-Fi, Thriller",
                true,
                5,
                "Tenet",
                "Tenet description"
            ),
            Film(
                2,
                R.drawable.black_widow,
                R.drawable.avengers_header_image,
                "13+",
                "Black Widow",
                "102 MIN",
                "38 REVIEWS",
                "Action, Adventure, Sci-Fi",
                false,
                4,
                "Black Widow",
                "Black Widow description"
            ),
            Film(
                3,
                R.drawable.wonder_woman,
                R.drawable.avengers_header_image,
                "13+",
                "Wonder Woman 1984",
                "97 MIN",
                "98 REVIEWS",
                "Action, Adventure, Fantasy",
                false,
                4,
                "Wonder Woman\n1984",
                "Wonder Woman description"
            ),
        )
    }

    fun getFilmById(id : Int) : Film {
        return getFilms()[id]
    }
}

