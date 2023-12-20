package com.mobile.areacounter.geometry

class Polygon(val vectors : List<Vector>) {

    private val triangles = mutableListOf<Triangle>()

    fun triangulate(){
        val angles = mutableListOf<Double>()

        for (index in 0..vectors.size-2){
            angles.add(GeometryHelper.calculateAngle(vectors[index], vectors[index+1]))
        }

        println(angles.map { Math.toDegrees(it) })

    }

}