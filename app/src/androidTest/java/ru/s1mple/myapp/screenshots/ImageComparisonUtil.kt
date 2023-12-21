package ru.s1mple.myapp.screenshots

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

object ImageComparisonUtil {

    fun saveImage(pathFile: File, image: Bitmap) {
        pathFile.mkdir()
        val imageFile = File(pathFile, "result.png")
        FileOutputStream(imageFile).use {
            image.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    /**
     * Return the difference in percent between two buffered images.
     *
     * @param img1 the first image.
     * @param img2 the second image.
     * @return difference percent.
     */
    fun getDifferencePercent(img1: Bitmap, img2: Bitmap): Float {
        val width: Int = img1.getWidth()
        val height: Int = img1.getHeight()
        var diff: Long = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                diff += pixelDiff(img1.getPixel(x, y), img2.getPixel(x, y)).toLong()
            }
        }
        val maxDiff = 3L * 255 * width * height
        return (100.0 * diff / maxDiff).toFloat()
    }

    /**
     * Compare two pixels
     *
     * @param rgb1 the first rgb
     * @param rgb2 the second rgn
     * @return the difference.
     */
    fun pixelDiff(rgb1: Int, rgb2: Int): Int {
        val r1 = rgb1 shr 16 and 0xff
        val g1 = rgb1 shr 8 and 0xff
        val b1 = rgb1 and 0xff
        val r2 = rgb2 shr 16 and 0xff
        val g2 = rgb2 shr 8 and 0xff
        val b2 = rgb2 and 0xff
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2)
    }
}