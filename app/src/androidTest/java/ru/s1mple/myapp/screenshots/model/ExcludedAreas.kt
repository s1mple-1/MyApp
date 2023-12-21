package ru.s1mple.myapp.screenshots.model

import android.graphics.Point


class ExcludedAreas {
    /**
     * The collection of the areas which would be excluded from the comparison.
     */
    val excluded: List<Rectangle>

    /**
     * Create empty instance of the [ExcludedAreas].
     */
    constructor() {
        excluded = ArrayList()
    }

    /**
     * Create instance of the [ExcludedAreas] with provided [Rectangle] areas.
     *
     * @param excluded provided collection of the [Rectangle] objects.
     */
    constructor(excluded: List<Rectangle>) {
        this.excluded = excluded
    }

    /**
     * Check if this [Point] contains in the [ExcludedAreas.excluded]
     * collection of the [Rectangle].
     *
     * @param point the [Point] object to be checked.
     *
     * @return `true` if this [Point] contains in areas from [ExcludedAreas.excluded].
     */
    operator fun contains(point: Point): Boolean {
        return excluded.stream().anyMatch { rectangle: Rectangle ->
            rectangle.containsPoint(
                point
            )
        }
    }
}