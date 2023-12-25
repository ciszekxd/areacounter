package com.mobile.areacounter.geometry

data class Point(val x : Double, val y : Double) {
    fun add(vector: Vector) : Point {
        return Point(x + vector.finishingPoint.x - vector.startingPoint.x, y + vector.finishingPoint.y - vector.startingPoint.y)
    }
}