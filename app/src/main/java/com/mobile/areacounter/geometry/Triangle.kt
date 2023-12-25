package com.mobile.areacounter.geometry

class Triangle(val pointA : Point, val pointB : Point, val pointC : Point) {
    fun belongsToTriangle(point: Point) : Boolean{
        val detv1 = calcVector(point, pointA, pointB)
        val detv2 = calcVector(point, pointB, pointC)
        val detv3 = calcVector(point, pointC, pointA)

        val hasNeg = detv1 < 0 || detv2 < 0 || detv3 < 0
        val hasPos = detv1 > 0 || detv2 > 0 || detv3 > 0

        return !(hasNeg && hasPos)
    }

    private fun calcVector(p1 : Point, p2 : Point, p3 : Point) : Double{
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y)
    }

    override fun equals(other: Any?): Boolean {
        other?.let {
            if (it is Triangle){
                return setOf(it.pointA, it.pointB, it.pointC) == setOf(pointA, pointB, pointC)
            }
        }

        return super.equals(other)
    }

    override fun hashCode(): Int {
        return setOf(pointA, pointB, pointC).hashCode()
    }

    override fun toString(): String {
        return setOf(pointA, pointB, pointC).toString()
    }
}