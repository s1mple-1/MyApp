package ru.s1mple.myapp.screenshots

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.Log
import androidx.core.graphics.scale
import ru.s1mple.myapp.screenshots.model.ExcludedAreas
import ru.s1mple.myapp.screenshots.model.ImageComparisonResult
import ru.s1mple.myapp.screenshots.model.Rectangle
import java.io.File
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

class ImageComparison(
    private val expected: Bitmap,
    private val actual: Bitmap,
    var destination: File
) {
    /**
     * The threshold which means the max distance between non-equal pixels.
     * Could be changed according to the size and requirements of the image.
     */
    var threshold = 5
        private set


    /**
     * Width of the line that is drawn the rectangle
     */
    var rectangleLineWidth = 2f
        private set


    /**
     * The number which marks how many rectangles. Beginning from 2.
     */
    private var counter = 2

    /**
     * The number of the marking specific rectangle.
     */
    private var regionCount = counter

    /**
     * The number of the minimal rectangle size. Count as (width x height).
     */
    var minimalRectangleSize = 1
        private set

    /**
     * Maximal count of the [Rectangle]s.
     * It means that would get the first x biggest rectangles.
     * Default value is -1, that means that all the rectangles would be drawn.
     */
    var maximalRectangleCount = -1
        private set

    /**
     * Level of the pixel tolerance. By default, it's 0.1 -> 10% difference.
     * The value can be set from 0.0 to 0.99.
     */
    var pixelToleranceLevel = 0.1
        private set

    /**
     * Constant using for counting the level of the difference.
     */
    private var differenceConstant: Double = calculateDifferenceConstant()

    /**
     * Matrix YxX => int[y][x].
     * E.g.:
     *   X - width ----
     *   .....................................
     *   . (0, 0)                            .
     *   .                                   .
     *   .                                   .
     *   .                                   .
     *   .                                   .
     *   .                                   .
     *   .                                   .
     *   .                                   .
     *   .                             (X, Y).
     *   .....................................
     */
    private var matrix: Array<IntArray> = Array(expected.height) { IntArray(expected.width) }

    /**
     * ExcludedAreas contains a List of [Rectangle]s to be ignored when comparing images
     */
    private var excludedAreas = ExcludedAreas()

    /**
     * Flag which says draw excluded rectangles or not.
     */
    var isDrawExcludedRectangles = false
        private set

    /**
     * The difference in percent between two images.
     */
    private val differencePercent = 0f

    /**
     * Flag for filling comparison difference rectangles.
     */
    var isFillDifferenceRectangles = false
        private set

    /**
     * Sets the opacity percentage of the fill of comparison difference rectangles. 0.0 means completely transparent and 100.0 means completely opaque.
     */
    var percentOpacityDifferenceRectangles = 20.0
        private set

    /**
     * Flag for filling excluded rectangles.
     */
    var isFillExcludedRectangles = false
        private set

    /**
     * Sets the opacity percentage of the fill of excluded rectangles. 0.0 means completely transparent and 100.0 means completely opaque.
     */
    var percentOpacityExcludedRectangles = 20.0
        private set

    /**
     * The percent of the allowing pixels to be different to stay [ImageComparisonState.MATCH] for comparison.
     * E.g. percent of the pixels, which would ignore in comparison.
     */
    var allowingPercentOfDifferentPixels = 0.0
        private set

    /**
     * Sets rectangle color of image difference. By default, it's red.
     */
    private var differenceRectangleColor: Int = Color.RED

    /**
     * Sets rectangle color of excluded part. By default, it's green.
     */
    private var excludedRectangleColor: Int = Color.GREEN

    /**
     * Draw rectangles which cover the regions of the difference pixels.
     *
     * @return the result of the drawing.
     */
    fun compareImages(): ImageComparisonResult {
        Log.i("ImageComparison", "compareImages()")
        // check that the images have the same size
        if (isImageSizesNotEqual(expected, actual)) {
            val actualResized: Bitmap = actual.scale(expected.width, expected.height)
            Log.i("ImageComparison", "end")
            return ImageComparisonResult.defaultSizeMisMatchResult(
                expected,
                actual,
                ImageComparisonUtil.getDifferencePercent(actualResized, expected)
            )
        }
        val rectangles = populateRectangles()
        if (rectangles.isEmpty()) {
            val matchResult = ImageComparisonResult.defaultMatchResult(expected, actual)
            if (isDrawExcludedRectangles) {
                Log.i("ImageComparison", "prepare result")
                matchResult.result = drawRectangles(rectangles)
                saveImageForDestination(matchResult.result)
            }
            Log.i("ImageComparison", "end")
            return matchResult
        }
        Log.i("ImageComparison", "prepare result")
        val resultImage: Bitmap = drawRectangles(rectangles)
        saveImageForDestination(resultImage)
        Log.i("ImageComparison", "end")
        return ImageComparisonResult.defaultMisMatchResult(
            expected,
            actual,
            ImageComparisonUtil.getDifferencePercent(actual, expected)
        )
            .setResult(resultImage)
            .setRectangles(rectangles)
    }

    /**
     * Check images for equals their widths and heights.
     *
     * @param expected [Bitmap] object of the expected image.
     * @param actual   [Bitmap] object of the actual image.
     * @return true if image size are not equal, false otherwise.
     */
    private fun isImageSizesNotEqual(expected: Bitmap, actual: Bitmap): Boolean {
        return expected.getHeight() != actual.getHeight() || expected.getWidth() != actual.getWidth()
    }

    /**
     * Populate binary matrix with "0" and "1". If the pixels are different set it as "1", otherwise "0".
     *
     * @return the count of different pixels
     */
    private fun populateTheMatrixOfTheDifferences(): Long {
        var countOfDifferentPixels: Long = 0
        var countOfPixels: Long = 0
        Log.i("ImageComparison", "populateTheMatrixOfTheDifferences")
        for (y in 0 until expected.height) {
            for (x in 0 until expected.width) {
                if (!excludedAreas.contains(Point(x, y))) {
                    countOfPixels++
                    if (isDifferentPixels(expected.getPixel(x, y), actual.getPixel(x, y))) {
                        matrix[y][x] = 1
                        countOfDifferentPixels++
                    }
                }
            }
        }
        Log.i("ImageComparison", "countOfPixels is $countOfPixels")
        Log.i("ImageComparison", "countOfDifferentPixels is $countOfDifferentPixels")
        return countOfDifferentPixels
    }

    /**
     * Say if the two pixels equal or not. The rule is the difference between two pixels
     * need to be more than [.pixelToleranceLevel].
     *
     * @param expectedRgb the RGB value of the Pixel of the Expected image.
     * @param actualRgb   the RGB value of the Pixel of the Actual image.
     * @return `true` if they' are difference, `false` otherwise.
     */
    private fun isDifferentPixels(expectedRgb: Int, actualRgb: Int): Boolean {
        if (expectedRgb == actualRgb) {
            return false
        } else if (pixelToleranceLevel == 0.0) {
            return true
        }
        val red1 = expectedRgb shr 16 and 0xff
        val green1 = expectedRgb shr 8 and 0xff
        val blue1 = expectedRgb and 0xff
        val red2 = actualRgb shr 16 and 0xff
        val green2 = actualRgb shr 8 and 0xff
        val blue2 = actualRgb and 0xff
        return (Math.pow((red2 - red1).toDouble(), 2.0) + Math.pow(
            (green2 - green1).toDouble(),
            2.0
        ) + Math.pow((blue2 - blue1).toDouble(), 2.0)
                > differenceConstant)
    }

    /**
     * Populate rectangles of the differences
     *
     * @return the collection of the populated [Rectangle] objects.
     */
    private fun populateRectangles(): List<Rectangle> {
        Log.i("ImageComparison", "populateRectangles")
        val countOfDifferentPixels = populateTheMatrixOfTheDifferences()
        if (countOfDifferentPixels == 0L) {
            return emptyList()
        }
        if (isAllowedPercentOfDifferentPixels(countOfDifferentPixels)) {
            return emptyList()
        }
        groupRegions()
        val rectangles: MutableList<Rectangle> = ArrayList()
        while (counter <= regionCount) {
            val rectangle = createRectangle()
            if (rectangle != Rectangle.createDefault() && rectangle.size() >= minimalRectangleSize) {
                rectangles.add(rectangle)
            }
            counter++
        }
        return mergeRectangles(mergeRectangles(rectangles))
    }

    /**
     * Say if provided {@param countOfDifferentPixels} is allowed for [ImageComparisonState.MATCH] state.
     *
     * @param countOfDifferentPixels the count of the different pixels in comparison.
     * @return true, if percent of different pixels lower or equal [ImageComparison.allowingPercentOfDifferentPixels],
     * false - otherwise.
     */
    private fun isAllowedPercentOfDifferentPixels(countOfDifferentPixels: Long): Boolean {
        val totalPixelCount = (matrix.size * matrix[0].size).toLong()
        Log.i("ImageComparison", "totalPixelCount is $totalPixelCount")
        val actualPercentOfDifferentPixels =
            countOfDifferentPixels.toDouble() / totalPixelCount.toDouble() * 100
        Log.i("ImageComparison", "actualPercentOfDifferentPixels is $actualPercentOfDifferentPixels")
        Log.i("ImageComparison", "allowingPercentOfDifferentPixels is $allowingPercentOfDifferentPixels")
        return actualPercentOfDifferentPixels <= allowingPercentOfDifferentPixels
    }

    /**
     * Create a [Rectangle] object.
     *
     * @return the [Rectangle] object.
     */
    private fun createRectangle(): Rectangle {
        val rectangle = Rectangle.createDefault()
        for (y in matrix.indices) {
            for (x in matrix[0].indices) {
                if (matrix[y][x] == counter) {
                    updateRectangleCreation(rectangle, x, y)
                }
            }
        }
        return rectangle
    }

    /**
     * Update [Point] of the rectangle based on x and y coordinates.
     */
    private fun updateRectangleCreation(rectangle: Rectangle, x: Int, y: Int) {
        if (x < rectangle.minPoint.x) {
            rectangle.minPoint.x = x
        }
        if (x > rectangle.maxPoint.x) {
            rectangle.maxPoint.x = x
        }
        if (y < rectangle.minPoint.y) {
            rectangle.minPoint.y = y
        }
        if (y > rectangle.maxPoint.y) {
            rectangle.maxPoint.y = y
        }
    }

    /**
     * Find overlapping rectangles and merge them.
     */
    private fun mergeRectangles(rectangles: MutableList<Rectangle>): MutableList<Rectangle> {
        var position = 0
        while (position < rectangles.size) {
            if (rectangles[position] == Rectangle.createZero()) {
                position++
            }
            for (i in 1 + position until rectangles.size) {
                val r1 = rectangles[position]
                val r2 = rectangles[i]
                if (r2 == Rectangle.createZero()) {
                    continue
                }
                if (r1.isOverlapping(r2)) {
                    rectangles[position] = r1.merge(r2)
                    r2.makeZeroRectangle()
                    if (position != 0) {
                        position--
                    }
                }
            }
            position++
        }
        return rectangles.stream().filter { it: Rectangle -> it != Rectangle.createZero() }
            .collect(Collectors.toList())
    }

    /**
     * Draw the rectangles based on collection of the rectangles and result image.
     *
     * @param rectangles the collection of the [Rectangle] objects.
     * @return result [Bitmap] with drawn rectangles.
     */
    private fun drawRectangles(rectangles: List<Rectangle>): Bitmap {
        val resultImage: Bitmap = actual
        val graphics = preparedGraphics2D(resultImage)
        drawExcludedRectangles(graphics)
        drawRectanglesOfDifferences(rectangles, graphics)
        return resultImage
    }

    /**
     * Draw excluded rectangles.
     *
     * @param graphics prepared [Graphics2D]object.
     */
    private fun drawExcludedRectangles(graphics: Canvas) {
        if (isDrawExcludedRectangles) {
            draw(graphics, excludedAreas.excluded, excludedRectangleColor)
            if (isFillExcludedRectangles) {
                fillRectangles(graphics, excludedAreas.excluded, percentOpacityExcludedRectangles)
            }
        }
    }

    /**
     * Draw rectangles with the differences.
     *
     * @param rectangles the collection of the [Rectangle] of differences.
     * @param graphics   prepared [Graphics2D]object.
     */
    private fun drawRectanglesOfDifferences(rectangles: List<Rectangle>, graphics: Canvas) {
        val rectanglesForDraw: List<Rectangle> =
            if (maximalRectangleCount > 0 && maximalRectangleCount < rectangles.size) {
                rectangles.stream()
                    .sorted(Comparator.comparing { obj: Rectangle -> obj.size() })
                    .skip((rectangles.size - maximalRectangleCount).toLong())
                    .collect(Collectors.toList())
            } else {
                ArrayList(rectangles)
            }
        draw(graphics, rectanglesForDraw, differenceRectangleColor)
        if (isFillDifferenceRectangles) {
            fillRectangles(graphics, rectanglesForDraw, percentOpacityDifferenceRectangles)
        }
    }

    /**
     * Prepare [Graphics2D] based on resultImage and rectangleLineWidth
     *
     * @param image image based on created [Graphics2D].
     * @return prepared [Graphics2D] object.
     */
    private fun preparedGraphics2D(image: Bitmap): Canvas {
        return Canvas(image)
    }

    /**
     * Сохраняет картинку
     *
     * @param картинка [Bitmap] для сохранения.
     */
    private fun saveImageForDestination(image: Bitmap) {
        if (Objects.nonNull(destination)) {
            ImageComparisonUtil.saveImage(destination, image)
        }
    }

    /**
     * Draw rectangles based on collection of the [Rectangle] and [Graphics2D].
     * getWidth/getHeight return real width/height,
     * so need to draw rectangle on one px smaller because minpoint + width/height is point on excluded pixel.
     *
     * @param graphics   the [Graphics2D] object for drawing.
     * @param rectangles the collection of the [Rectangle].
     */
    private fun draw(graphics: Canvas, rectangles: List<Rectangle>, color: Int) {
        val paint = Paint().apply {
            this.color = color
            style = Paint.Style.STROKE
            strokeWidth = rectangleLineWidth
        }

        rectangles.forEach(
            Consumer<Rectangle> { rectangle: Rectangle ->
                graphics.drawRect(
                    rectangle.minPoint.x.toFloat(),
                    rectangle.minPoint.y.toFloat(),
                    rectangle.maxPoint.x.toFloat(),
                    rectangle.maxPoint.y.toFloat(),

                    paint
                )

            }
        )
    }

    /**
     * Fill rectangles based on collection of the [Rectangle] and [Graphics2D].
     * getWidth/getHeight return real width/height,
     * so need to draw rectangle fill two px smaller to fit inside rectangle borders.
     *
     * @param graphics       the [Graphics2D] object for drawing.
     * @param rectangles     rectangles the collection of the [Rectangle].
     * @param percentOpacity the opacity of the fill.
     */
    private fun fillRectangles(
        graphics: Canvas,
        rectangles: List<Rectangle>,
        percentOpacity: Double,
    ) {
        val paint = Paint().apply {
            color =
                Color.argb((percentOpacity / 100 * 255).toInt(), Color.RED, Color.GREEN, Color.BLUE)
            style = Paint.Style.STROKE
            strokeWidth = rectangleLineWidth
        }
        rectangles.forEach(
            Consumer<Rectangle> { rectangle: Rectangle ->
                graphics.drawRect(
                    rectangle.minPoint.x.toFloat(),
                    rectangle.minPoint.y.toFloat(),
                    rectangle.maxPoint.x.toFloat(),
                    rectangle.maxPoint.y.toFloat(),
                    paint
                )
            }
        )
    }

    /**
     * Group rectangle regions in matrix.
     */
    private fun groupRegions() {
        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                if (matrix[y][x] == 1) {
                    joinToRegion(x, y)
                    regionCount++
                }
            }
        }
    }

    /**
     * The recursive method which go to all directions and finds difference
     * in binary matrix using `threshold` for setting max distance between values which equal "1".
     * and set the `groupCount` to matrix.
     *
     * @param x the value of the X-coordinate.
     * @param y the value of the Y-coordinate.
     */
    private fun joinToRegion(x: Int, y: Int) {
        if (isJumpRejected(x, y)) {
            return
        }
        matrix[y][x] = regionCount
        for (i in 0 until threshold) {
            joinToRegion(x + 1 + i, y)
            joinToRegion(x, y + 1 + i)
            joinToRegion(x + 1 + i, y - 1 - i)
            joinToRegion(x - 1 - i, y + 1 + i)
            joinToRegion(x + 1 + i, y + 1 + i)
        }
    }

    /**
     * Returns the list of rectangles that would be drawn as a diff image.
     * If you submit two images that are the same barring the parts you want to excludedAreas you get a list of
     * rectangles that can be used as said excludedAreas
     *
     * @return List of [Rectangle]
     */
    fun createMask(): List<Rectangle> {
        return populateRectangles()
    }

    /**
     * Check next step valid or not.
     *
     * @param x X-coordinate of the image.
     * @param y Y-coordinate of the image
     * @return true if jump rejected, otherwise false.
     */
    private fun isJumpRejected(x: Int, y: Int): Boolean {
        return y < 0 || y >= matrix.size || x < 0 || x >= matrix[y].size || matrix[y][x] != 1
    }

    fun setPixelToleranceLevel(pixelToleranceLevel: Double): ImageComparison {
        if (0.0 <= pixelToleranceLevel && pixelToleranceLevel < 1) {
            this.pixelToleranceLevel = pixelToleranceLevel
            differenceConstant = calculateDifferenceConstant()
        }
        return this
    }

    private fun calculateDifferenceConstant(): Double {
        return Math.pow(pixelToleranceLevel * Math.sqrt(Math.pow(255.0, 2.0) * 3), 2.0)
    }

    fun setDrawExcludedRectangles(drawExcludedRectangles: Boolean): ImageComparison {
        isDrawExcludedRectangles = drawExcludedRectangles
        return this
    }

    fun setThreshold(threshold: Int): ImageComparison {
        this.threshold = threshold
        return this
    }

    fun getDestination(): Optional<File> {
        return Optional.ofNullable(destination)
    }

    fun setDestination(destination: File): ImageComparison {
        this.destination = destination
        return this
    }

    fun getExpected(): Bitmap {
        return expected
    }

    fun getActual(): Bitmap {
        return actual
    }

    fun setRectangleLineWidth(rectangleLineWidth: Float): ImageComparison {
        this.rectangleLineWidth = rectangleLineWidth
        return this
    }

    fun setMinimalRectangleSize(minimalRectangleSize: Int): ImageComparison {
        this.minimalRectangleSize = minimalRectangleSize
        return this
    }

    fun setMaximalRectangleCount(maximalRectangleCount: Int): ImageComparison {
        this.maximalRectangleCount = maximalRectangleCount
        return this
    }

    fun setExcludedAreas(excludedAreas: List<Rectangle>): ImageComparison {
        this.excludedAreas = ExcludedAreas(excludedAreas)
        return this
    }

    fun setDifferenceRectangleFilling(
        fillRectangles: Boolean,
        percentOpacity: Double
    ): ImageComparison {
        isFillDifferenceRectangles = fillRectangles
        percentOpacityDifferenceRectangles = percentOpacity
        return this
    }

    fun setExcludedRectangleFilling(
        fillRectangles: Boolean,
        percentOpacity: Double
    ): ImageComparison {
        isFillExcludedRectangles = fillRectangles
        percentOpacityExcludedRectangles = percentOpacity
        return this
    }

    fun setAllowingPercentOfDifferentPixels(allowingPercentOfDifferentPixels: Double): ImageComparison {
        if (0.0 <= allowingPercentOfDifferentPixels && allowingPercentOfDifferentPixels <= 100) {
            this.allowingPercentOfDifferentPixels = allowingPercentOfDifferentPixels
        } else {
            //todo add warning here
        }
        return this
    }

    fun getDifferenceRectangleColor(): Int {
        return differenceRectangleColor
    }

    fun setDifferenceRectangleColor(differenceRectangleColor: Int): ImageComparison {
        this.differenceRectangleColor = differenceRectangleColor
        return this
    }

    fun getExcludedRectangleColor(): Int {
        return excludedRectangleColor
    }

    fun setExcludedRectangleColor(excludedRectangleColor: Int): ImageComparison {
        this.excludedRectangleColor = excludedRectangleColor
        return this
    }
}