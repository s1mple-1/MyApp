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
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe.",
                listOf(
                    Actor("Robert\nDowney Jr.", R.drawable.robert_downey_jr),
                    Actor("Chris\nEvans", R.drawable.chris_evans),
                    Actor("Mark\nRuffalo", R.drawable.mark_ruffalo),
                    Actor("Chris\nHemsworth", R.drawable.chris_hemsworth),
                )
            ),
            Film(
                1,
                R.drawable.tenet_main,
                R.drawable.tenet_header_image,
                "16+",
                "Tenet",
                "97 MIN",
                "98 REVIEWS",
                "Action, Sci-Fi, Thriller",
                true,
                5,
                "Tenet",
                "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
                listOf(
                    Actor("Name", R.drawable.robert_downey_jr),
                    Actor("Name", R.drawable.robert_downey_jr),
                    Actor("Name", R.drawable.robert_downey_jr),
                    Actor("Name", R.drawable.robert_downey_jr),
                )
            ),
            Film(
                2,
                R.drawable.black_widow,
                R.drawable.black_widow_header_image,
                "13+",
                "Black Widow",
                "102 MIN",
                "38 REVIEWS",
                "Action, Adventure, Sci-Fi",
                false,
                4,
                "Black Widow",
                "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
                listOf(
                    Actor("Robert\nDowney Jr.", R.drawable.robert_downey_jr),
                    Actor("Chris\nEvans", R.drawable.robert_downey_jr),
                    Actor("Mark\nRuffalo", R.drawable.robert_downey_jr),
                    Actor("Chris\nHemsworth", R.drawable.robert_downey_jr),
                )
            ),
            Film(
                3,
                R.drawable.wonder_woman,
                R.drawable.wonder_woman_1984_header_image,
                "13+",
                "Wonder Woman 1984",
                "97 MIN",
                "98 REVIEWS",
                "Action, Adventure, Fantasy",
                false,
                4,
                "Wonder Woman\n1984",
                "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                listOf(
                    Actor("Name", R.drawable.robert_downey_jr),
                    Actor("Name", R.drawable.robert_downey_jr),
                    Actor("Name", R.drawable.robert_downey_jr),
                    Actor("Name", R.drawable.robert_downey_jr),
                )
            ),
        )
    }

    fun getFilmById(id: Int): Film {
        return getFilms()[id]
    }
}

