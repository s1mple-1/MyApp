package ru.s1mple.myapp.screenshots.model

import android.graphics.Bitmap
import ru.s1mple.myapp.screenshots.ImageComparisonUtil
import java.io.File


/**
 * Data transfer objects which contains all the needed data for result of the comparison.
 */
class ImageComparisonResult(
    expected: Bitmap,
    actual: Bitmap
) {


    /**
     * [Bitmap] object of the comparison result.
     */
    lateinit var result: Bitmap

    /**
     * State of the comparison.
     */
    var imageComparisonState: ImageComparisonState? = null
        private set

    /**
     * The difference percentage between two Bitmap.
     */
    var differencePercent = 0f
        private set

    /**
     * Rectangles of the differences
     */
    var rectangles: List<Rectangle>? = null
        private set

    /**
     * Save the image to the provided [File] object.
     *
     * @param file the provided [File] object.
     * @return this [ImageComparisonResult] object.
     */
    fun writeResultTo(file: File): ImageComparisonResult {
        ImageComparisonUtil.saveImage(file, result)
        return this
    }


    fun setResult(result: Bitmap): ImageComparisonResult{
        this.result = result
        return this
    }

    fun setImageComparisonState(imageComparisonState: ImageComparisonState): ImageComparisonResult {
        this.imageComparisonState = imageComparisonState
        return this
    }

    fun setDifferencePercent(differencePercent: Float): ImageComparisonResult {
        this.differencePercent = differencePercent
        return this
    }

    fun setRectangles(rectangles: List<Rectangle>): ImageComparisonResult {
        this.rectangles = rectangles
        return this
    }

    companion object {
        /**
         * Create default instance of the [ImageComparisonResult] with [ImageComparisonState.SIZE_MISMATCH].
         *
         * @param expected          expected [BufferedImage] object.
         * @param actual            actual [BufferedImage] object.
         * @param differencePercent the percent of the differences between images.
         * @return instance of the [ImageComparisonResult] object.
         */
        fun defaultSizeMisMatchResult(
            expected: Bitmap, actual: Bitmap,
            differencePercent: Float
        ): ImageComparisonResult {
            return ImageComparisonResult(expected, actual)
                .setImageComparisonState(ImageComparisonState.SIZE_MISMATCH)
                .setDifferencePercent(differencePercent)
                .setResult(actual)
        }

        /**
         * Create default instance of the [ImageComparisonResult] with [ImageComparisonState.MISMATCH].
         *
         * @param expected expected [BufferedImage] object.
         * @param actual   actual [BufferedImage] object.
         * @param differencePercent the persent of the differences between images.
         * @return instance of the [ImageComparisonResult] object.
         */
        fun defaultMisMatchResult(
            expected: Bitmap,
            actual: Bitmap,
            differencePercent: Float
        ): ImageComparisonResult {
            return ImageComparisonResult(expected, actual)
                .setImageComparisonState(ImageComparisonState.MISMATCH)
                .setDifferencePercent(differencePercent)
                .setResult(actual)
        }

        /**
         * Create default instance of the [ImageComparisonResult] with [ImageComparisonState.MATCH].
         *
         * @param expected expected [BufferedImage] object.
         * @param actual   actual [BufferedImage] object.
         * @return instance of the [ImageComparisonResult] object.
         */
        fun defaultMatchResult(
            expected: Bitmap,
            actual: Bitmap
        ): ImageComparisonResult {
            return ImageComparisonResult(expected, actual)
                .setImageComparisonState(ImageComparisonState.MATCH)
                .setResult(actual)
        }
    }
}