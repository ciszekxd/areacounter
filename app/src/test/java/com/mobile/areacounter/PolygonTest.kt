package com.mobile.areacounter

import com.mobile.areacounter.geometry.Point
import com.mobile.areacounter.geometry.Polygon
import com.mobile.areacounter.geometry.PolygonMinMax
import com.mobile.areacounter.geometry.Triangle
import com.mobile.areacounter.geometry.Vector
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PolygonTest {

    companion object {
        @JvmStatic
        fun vectorSource(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(
                    listOf(
                        Vector(Point(2.0,2.0), Point(5.0, 0.0)),
                        Vector(Point(5.0, 0.0), Point(4.0, -4.0)),
                        Vector(Point(4.0, -4.0), Point(-5.0, -3.0)),
                        Vector(Point(-5.0, -3.0), Point(-6.0, 1.0)),
                        Vector(Point(-6.0,1.0), Point(2.0,2.0))
                    ),
                    PolygonMinMax(-6.0, -4.0, 5.0, 2.0)
                ),
                Arguments.of(
                    listOf(
                        Vector(Point(5.0,5.0), Point(5.0, -5.0)),
                        Vector(Point(5.0, -5.0), Point(-5.0, -5.0)),
                        Vector(Point(-5.0, -5.0), Point(-5.0, 5.0)),
                        Vector(Point(-5.0, 5.0), Point(5.0, 5.0))
                    ),
                    PolygonMinMax(-5.0, -5.0, 5.0, 5.0)
                ),
                Arguments.of(
                    listOf(
                        Vector(Point(0.0,6.0), Point(5.0, -7.0)),
                        Vector(Point(5.0, -7.0), Point(-5.0, -8.0)),
                        Vector(Point(-5.0, -8.0), Point(0.0, 6.0))
                    ),
                    PolygonMinMax(-5.0, -8.0, 5.0, 6.0)
                )
            )
        }

        @JvmStatic
        fun triangulationVectorSource(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(
                    listOf(
                        Vector(Point(5.0, 5.0), Point(5.0, -5.0)),
                        Vector(Point(5.0, -5.0), Point(-5.0, -5.0)),
                        Vector(Point(-5.0, -5.0), Point(-5.0, 5.0)),
                        Vector(Point(-5.0, 5.0), Point(5.0, 5.0))
                    ),
                    setOf(
                        Triangle(
                            Point(5.0, 5.0),
                            Point(-5.0, -5.0),
                            Point(-5.0, 5.0)
                        ),
                        Triangle(
                            Point(5.0, 5.0),
                            Point(5.0, -5.0),
                            Point(-5.0, -5.0)
                        )
                    )
                ),
                Arguments.of(
                    listOf(
                        Vector(Point(2.0, 2.0), Point(5.0, 0.0)),
                        Vector(Point(5.0, 0.0), Point(4.0, -4.0)),
                        Vector(Point(4.0, -4.0), Point(-5.0, -3.0)),
                        Vector(Point(-5.0, -3.0), Point(-6.0, 1.0)),
                        Vector(Point(-6.0, 1.0), Point(2.0, 2.0))
                    ),
                    setOf(
                        Triangle(
                            Point(2.0, 2.0),
                            Point(-6.0, 1.0),
                            Point(-5.0, -3.0)
                        ),
                        Triangle(
                            Point(2.0, 2.0),
                            Point(-5.0, -3.0),
                            Point(4.0, -4.0)
                        ),
                        Triangle(
                            Point(2.0, 2.0),
                            Point(5.0, 0.0),
                            Point(4.0, -4.0)
                        )
                    )
                ),
                Arguments.of(
                    listOf(
                        Vector(Point(6.0, 5.0), Point(5.0, 5.0)),
                        Vector(Point(5.0, 5.0), Point(5.0, 1.0)),
                        Vector(Point(5.0, 1.0), Point(8.0, 1.0)),
                        Vector(Point(8.0, 1.0), Point(8.0, 5.0)),
                        Vector(Point(8.0, 5.0), Point(7.0, 5.0)),
                        Vector(Point(7.0, 5.0), Point(7.0, 2.0)),
                        Vector(Point(7.0, 2.0), Point(6.0, 2.0)),
                        Vector(Point(6.0, 2.0), Point(6.0, 5.0)),
                    ),
                    setOf(
                        Triangle(Point(5.0, 1.0), Point(5.0, 5.0), Point(6.0, 5.0)),
                        Triangle(Point(5.0, 1.0), Point(6.0, 5.0), Point(6.0, 2.0)),
                        Triangle(Point(5.0, 1.0), Point(6.0, 2.0), Point(8.0, 1.0)),
                        Triangle(Point(6.0, 2.0), Point(7.0, 2.0), Point(8.0, 1.0)),
                        Triangle(Point(8.0, 1.0), Point(7.0, 2.0), Point(8.0, 5.0)),
                        Triangle(Point(7.0, 2.0), Point(7.0, 5.0), Point(8.0, 5.0))
                    )
                )
            )
        }
    }



    @ParameterizedTest
    @MethodSource("triangulationVectorSource")
    fun testPolygon(vectorList: List<Vector>, expectedTriangleSet: Set<Triangle>){
        val polygon = Polygon(vectorList)

        val triangleSet = polygon.triangulate()

        Assertions.assertThat(triangleSet).isEqualTo(expectedTriangleSet)
    }

    @ParameterizedTest()
    @MethodSource("vectorSource")
    fun getExtremesTest(vectors : List<Vector>, expectedResult : PolygonMinMax){
        val polygon = Polygon(vectors)

        val result = polygon.getExtremes()

        Assertions.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun triangleEqualsTest(){
        val pointA = Point(5.0,5.0)
        val pointB = Point(4.0, 12.0)
        val pointC = Point(8.0, 23.0)
        val triangle1 = Triangle(pointA, pointB, pointC)
        val triangle2 = Triangle(pointA, pointC, pointB)

        Assertions.assertThat(triangle1).isEqualTo(triangle2)
        Assertions.assertThat(setOf(triangle1) == setOf(triangle2)).isTrue()
    }
}