package com.mobile.areacounter.geometry

import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

object GeometryHelper {
    fun calculateAngle(firstVector: Vector, secondVector: Vector) : Double {
        val firstVectorCommonStart = Point(
            firstVector.finishingPoint.x - firstVector.startingPoint.x,
            firstVector.finishingPoint.y - firstVector.startingPoint.y)

        val secondVectorCommonStart = Point(
            secondVector.finishingPoint.x - secondVector.startingPoint.x,
            secondVector.finishingPoint.y - secondVector.startingPoint.x)

        val vectorsDotProduct = firstVectorCommonStart.x * secondVectorCommonStart.x + firstVectorCommonStart.y * secondVectorCommonStart.y

        val absA = calcAbs(firstVectorCommonStart)
        val absB = calcAbs(secondVectorCommonStart)

        return acos(vectorsDotProduct / (absA * absB))
    }

    private fun calcAbs(a : Point) : Double{
        return sqrt(a.x.pow(2.0) + a.y.pow(2.0))
    }
}