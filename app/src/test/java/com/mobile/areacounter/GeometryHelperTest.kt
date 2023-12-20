package com.mobile.areacounter

import com.mobile.areacounter.geometry.GeometryHelper
import com.mobile.areacounter.geometry.Point
import com.mobile.areacounter.geometry.Vector
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GeometryHelperTest {

    companion object {
        @JvmStatic
        fun vectorSource(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(
                    Vector(Point(0.0, 0.0), Point(0.0, 5.0)),
                    Vector(Point(5.0, 5.0), Point(7.0, 5.0)),
                    90, 0
                ),
                Arguments.of(
                    Vector(Point(0.0, 0.0), Point(1.0, 0.0)),
                    Vector(Point(0.0, 0.0), Point(-1.0, 0.0)),
                    180.0
                ),
                Arguments.of(
                    Vector(Point(0.0, 0.0), Point(0.0, 1.0)),
                    Vector(Point(0.0, 0.0), Point(-1.0, 0.0)),
                    90.0
                ),
                Arguments.of(
                    Vector(Point(0.0, 0.0), Point(1.0, 0.0)),
                    Vector(Point(0.0, 0.0), Point(-1.0, -1.0)),
                    135.0
                )
            )
        }
    }

    @ParameterizedTest()
    @MethodSource("vectorSource")
    fun shouldReturnAngle(vec1 : Vector, vec2 : Vector, expected : Double){
        val res = Math.toDegrees(GeometryHelper.calculateAngle(vec1, vec2))
        Assertions.assertThat(res).isEqualTo(expected)
    }

}