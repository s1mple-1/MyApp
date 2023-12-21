package ru.s1mple.myapp.screenshots.model

enum class ImageComparisonState {

    /**
     * Result state of the comparison, where mismatch of the image sizes.
     */
    SIZE_MISMATCH,

    /**
     * Result state of the comparison, where mismatch of the images.
     */
    MISMATCH,

    /**
     * Result state of the images, where images are equal, e.g. match.
     */
    MATCH
}