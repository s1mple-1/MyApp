package ru.s1mple.myapp.screenshots.model

import android.graphics.Point
import java.util.*

/**
 * Object contained data for a rectangle.
 */
class Rectangle {
    
    /**
     * Left Top [Point] on the [Rectangle].
     */
    var minPoint: Point

    /**
     * Right bottom [Point] on the [Rectangle].
     */
    var maxPoint: Point

    /**
     * Create empty instance of the [Rectangle].
     */
    private constructor() {
        minPoint = Point()
        maxPoint = Point()
    }

    /**
     * Create clone object based on the provided [Rectangle].
     *
     * @param rectangle provided [Rectangle] object.
     */
    constructor(rectangle: Rectangle) {
        minPoint = Point(rectangle.minPoint.x, rectangle.minPoint.y)
        maxPoint = Point(rectangle.maxPoint.x, rectangle.maxPoint.y)
    }

    /**
     * Create instance of the [Rectangle] based on the provided coordinates.
     *
     * @param minX minimal X-coordinate.
     * @param minY minimal Y-coordinate.
     * @param maxX maximal X-coordinate.
     * @param maxY maximal Y-coordinate.
     */
    constructor(minX: Int, minY: Int, maxX: Int, maxY: Int) {
        minPoint = Point(minX, minY)
        maxPoint = Point(maxX, maxY)
    }

    /**
     * Create new [Rectangle] via merging this and that.
     *
     * @param that [Rectangle] for merging with this.
     * @return new merged [Rectangle].
     */
    fun merge(that: Rectangle): Rectangle {
        return Rectangle(
            Integer.min(
                minPoint.x, that.minPoint.x
            ),
            Integer.min(minPoint.y, that.minPoint.y),
            Integer.max(maxPoint.x, that.maxPoint.x),
            Integer.max(maxPoint.y, that.maxPoint.y)
        )
    }

    /**
     * Check is that rectangle overlap this.
     *
     * @param that [Rectangle] which checks with this.
     * @return true if this over lapp that, false otherwise.
     */
    fun isOverlapping(that: Rectangle): Boolean {
        return if (maxPoint.y < that.minPoint.y ||
            that.maxPoint.y < minPoint.y
        ) {
            false
        } else maxPoint.x >= that.minPoint.x &&
                that.maxPoint.x >= minPoint.x
    }

    /**
     * Set default values for rectangle.
     */
    fun setDefaultValues() {
        maxPoint = Point(Int.MIN_VALUE, Int.MIN_VALUE)
        minPoint = Point(Int.MAX_VALUE, Int.MAX_VALUE)
    }

    /**
     * Make zero rectangle.
     */
    fun makeZeroRectangle() {
        minPoint = Point()
        maxPoint = Point()
    }

    /**
     * Size of the [Rectangle], counted as width x height.
     *
     * @return the size of the [Rectangle].
     */
    fun size(): Int {
        return width * height
    }

    /**
     * Count the width of the [Rectangle].
     * Min and max point are included, so real width is +1px
     *
     * @return rectangle width.
     */
    private val width: Int
        get() = maxPoint.x - minPoint.x + 1

    /**
     * Count the height of the [Rectangle].
     * Min and max point are included, so real width is +1px.
     *
     * @return rectangle height.
     */
    private val height: Int
        get() = maxPoint.y - minPoint.y + 1

    /**
     * Check in the provided [Point] contains in the [Rectangle].
     *
     * @param point provided [Point].
     * @return `true` if provided [Point] contains, `false` - otherwise.
     */
    fun containsPoint(point: Point): Boolean {
        return point.x >= minPoint.x && point.x <= maxPoint.x && point.y >= minPoint.y && point.y <= maxPoint.y
    }
    

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val rectangle = other as Rectangle
        return minPoint == rectangle.minPoint && maxPoint == rectangle.maxPoint
    }

    override fun hashCode(): Int {
        return Objects.hash(minPoint, maxPoint)
    }

    companion object {
        /**
         * Create default [Rectangle] object.
         *
         * @return default rectangle [Rectangle].
         */
        fun createDefault(): Rectangle {
            val defaultRectangle = Rectangle()
            defaultRectangle.setDefaultValues()
            return defaultRectangle
        }

        /**
         * Create instance with zero points.
         *
         * @return created [Rectangle] instance.
         */
        fun createZero(): Rectangle {
            val rectangle = Rectangle()
            rectangle.makeZeroRectangle()
            return rectangle
        }
    }
}