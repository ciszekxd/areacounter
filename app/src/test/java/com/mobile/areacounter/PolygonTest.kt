package com.mobile.areacounter

import com.mobile.areacounter.geometry.Point
import com.mobile.areacounter.geometry.Polygon
import com.mobile.areacounter.geometry.Vector
import org.junit.jupiter.api.Test

class PolygonTest {

    @Test
    fun testPolygon(){
        val polygon = Polygon(
            listOf(
                Vector(Point(2.0,2.0), Point(5.0, 0.0)),
                Vector(Point(5.0, 0.0), Point(4.0, -4.0)),
                Vector(Point(4.0, -4.0), Point(-5.0, -3.0)),
                Vector(Point(-5.0, -3.0), Point(-6.0, 1.0)),
                Vector(Point(-6.0,1.0), Point(2.0,2.0))
            ))

        polygon.triangulate()
    }
}