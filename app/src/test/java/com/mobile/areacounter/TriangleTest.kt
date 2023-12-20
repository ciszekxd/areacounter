package com.mobile.areacounter

import com.mobile.areacounter.geometry.Point
import com.mobile.areacounter.geometry.Triangle
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class TriangleTest {

    companion object {
        @JvmStatic
        fun correctPointSource(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(Point(1.0, 1.0)),
                Arguments.of(Point(2.0, 2.0)),
                Arguments.of(Point(0.0, 5.0)))
        }

        @JvmStatic
        fun inCorrectPointSource(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(Point(6.0, 0.0)),
                Arguments.of(Point(0.0, 6.0)),
                Arguments.of(Point(5.0, 5.0)))
        }
    }
    @ParameterizedTest
    @MethodSource("correctPointSource")
    fun shouldCorrectlyCalculateIfPointBelongsToTriangle(point : Point) {
        val triangle = Triangle(Point(0.0, 0.0), Point(0.0, 5.0), Point(5.0, 0.0))
        val res = triangle.belongsToTriangle(point)
        Assertions.assertThat(res).isTrue()
    }

    @ParameterizedTest
    @MethodSource("inCorrectPointSource")
    fun shouldCorrectlyCalculateIfPointDoesNotBelongToTriangle(point : Point) {
        val triangle = Triangle(Point(0.0, 0.0), Point(0.0, 5.0), Point(5.0, 0.0))
        val res = triangle.belongsToTriangle(point)
        Assertions.assertThat(res).isFalse()
    }
}