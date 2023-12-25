package com.mobile.areacounter.geometry

data class Vector(val startingPoint: Point, val finishingPoint: Point) {
    fun isRising(): Boolean {
        return finishingPoint.y - startingPoint.y > 0
    }

    fun isLeftToRight(): Boolean {
        return finishingPoint.x - startingPoint.x > 0
    }

    fun isVertical(): Boolean {
        return finishingPoint.x - startingPoint.x == 0.0
    }
}
