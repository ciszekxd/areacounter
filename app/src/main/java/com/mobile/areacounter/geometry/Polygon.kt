package com.mobile.areacounter.geometry

import kotlin.random.Random

class Polygon(val vectors : List<Vector>) {

    private val triangles = mutableListOf<Triangle>()
    private val relativePoints = mutableListOf<Point>()
    private val random = Random(5)

    fun triangulate() : Set<Triangle> {
        if (vectors.size == 3){
            return setOf(
                Triangle(
                    vectors[0].startingPoint,
                    vectors[1].startingPoint,
                    vectors[2].startingPoint))
        }

        val angles = mutableListOf<Pair<Double, Double>>()

        for (index in 0..vectors.size-2){
            angles.add(GeometryHelper.calculateInnerAndOuterAngle(vectors[index], vectors[index+1]))
        }
        angles.add(GeometryHelper.calculateInnerAndOuterAngle(vectors[vectors.size-1], vectors[0]))

        val leftSum = angles.sumOf { it.first }
        val rightSum = angles.sumOf { it.second }

        val innerAngles = if (leftSum < rightSum) {
            angles.map { it.first }

        } else {
            angles.map { it.second }
        }.toMutableList()

        val roundedVectorList = vectors.toMutableList()
        roundedVectorList.add(vectors[0])

        val minAngleIndex = innerAngles.indexOf(innerAngles.min())

        val cutVector1 = roundedVectorList[minAngleIndex]
        val cutVector2 = roundedVectorList[minAngleIndex+1]

        val newVector = Vector(cutVector1.startingPoint, cutVector2.finishingPoint)

        val newPolygonVectors = vectors.filter { !setOf(cutVector1, cutVector2).contains(it) }.toMutableList()

        val indexToInsert = newPolygonVectors.indexOf(newPolygonVectors.filter{it.finishingPoint == newVector.startingPoint}[0])

        newPolygonVectors.add(indexToInsert+1, newVector)

        return Polygon(newPolygonVectors).triangulate()
            .plus(Triangle(cutVector1.startingPoint, cutVector1.finishingPoint, cutVector2.finishingPoint))

    }

    fun getExtremes() : PolygonMinMax {
        if (relativePoints.isEmpty()){
            calculateRelativePoints()
        }

        var maxX = Double.MIN_VALUE
        var maxY = Double.MIN_VALUE
        var minX = Double.MAX_VALUE
        var minY = Double.MAX_VALUE

        relativePoints.forEach{
            if(it.x > maxX){
                maxX = it.x
            }

            if (it.x < minX){
                minX = it.x
            }

            if (it.y > maxY){
                maxY = it.y
            }

            if (it.y < minY){
                minY = it.y
            }
        }

        return PolygonMinMax(minX, minY, maxX, maxY)
    }

    fun getPointOutsideOfPolygon() : Point{
        val extremePosition = getExtremes()
        val from = 0.0
        val to = 10.0
        return Point(extremePosition.xMax + random.nextDouble(from, to), extremePosition.yMax + random.nextDouble(from, to))
    }

    private fun calculateRelativePoints(){
        var lastPoint = Point(vectors[0].startingPoint.x, vectors[0].startingPoint.y)

        relativePoints.add(lastPoint)

        vectors.forEach { vector ->
            lastPoint = lastPoint.add(vector)
            relativePoints.add(lastPoint)
        }
    }


}