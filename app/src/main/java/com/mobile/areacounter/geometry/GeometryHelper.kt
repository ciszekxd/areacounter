package com.mobile.areacounter.geometry

import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

object GeometryHelper {
    val random = Random

    fun calculateAngle(firstVector: Vector, secondVector: Vector) : Double {
        val firstVectorCommonStart = Point(
            firstVector.finishingPoint.x - firstVector.startingPoint.x,
            firstVector.finishingPoint.y - firstVector.startingPoint.y)

        val secondVectorCommonStart = Point(
            secondVector.finishingPoint.x - secondVector.startingPoint.x,
            secondVector.finishingPoint.y - secondVector.startingPoint.y)

        val vectorsDotProduct = firstVectorCommonStart.x * secondVectorCommonStart.x + firstVectorCommonStart.y * secondVectorCommonStart.y

        val absA = calcAbs(firstVectorCommonStart)
        val absB = calcAbs(secondVectorCommonStart)

        return acos(vectorsDotProduct / (absA * absB))
    }

    private fun calcAbs(a : Point) : Double{
        return sqrt(a.x.pow(2.0) + a.y.pow(2.0))
    }

    fun calculateInnerAndOuterAngle(v1 : Vector, v2 : Vector) : Pair<Double, Double> {

        val alpha = Math.toDegrees(calculateAngle(v1, v2))

        if (bendsToLeft(v1, v2)){
            return Pair(180.0 - alpha, 180.0 + alpha)
        }

        return Pair(180.0 + alpha, 180.0 - alpha)

    }

    fun calculateMonteCarloArea(polygon: Polygon, probes : Int = 1000): Double {
        val extremePoints = polygon.getExtremes()

        var pointsInside = 0

        for (index in 0..probes){
            if (polygon.triangulate().filter {
                    it.belongsToTriangle(
                        Point(
                            random.nextDouble(extremePoints.xMin, extremePoints.xMax),
                            random.nextDouble(extremePoints.yMin, extremePoints.yMax)
                        )
                    )
                }.toSet().isNotEmpty()){
                pointsInside += 1
            }
        }
        val squareX = extremePoints.xMax - extremePoints.xMin
        val squareY = extremePoints.yMax - extremePoints.yMin
        val squareArea = squareX * squareY

        return squareArea * pointsInside / probes
    }

    private fun bendsToLeft(v1: Vector, v2: Vector): Boolean  {
        return (v1.isVertical() && v1.isRising() && v2.finishingPoint.x < v1.startingPoint.x) ||
                (v1.isVertical() && !v1.isRising() && v2.finishingPoint.x > v1.startingPoint.x) ||
                (!v1.isVertical() && v1.isLeftToRight() && !Line.fromVector(v1).isGreaterThan(v2.finishingPoint)) ||
                (!v1.isVertical() && !v1.isLeftToRight() && Line.fromVector(v1).isGreaterThan(v2.finishingPoint))
    }
}