package catnemo.top.opengleslearn.javagl.graphics.builder

import kotlin.math.sqrt

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/29
 *
 */
class Geometry {
    data class Point(val x: Float, val y: Float, val z: Float) {

        fun translateY(distance: Float): Point {
            return Point(x, y + distance, z)
        }
    }

    data class Circle(val center: Point, val radius: Float) {
        fun scale(scale: Float): Circle {
            return Circle(center, radius * scale)
        }
    }

    data class Conde(val center: Point, val radius: Float, val height: Float)

    data class Cylinder(val center: Point, val radius: Float, val height: Float)

    data class Vector(val x: Float, val y: Float, val z: Float) {
        val length: Float = sqrt(x * x + y * y + z * z)

        fun crossProduct(other: Vector) = Vector(
                (y * other.z) - (z * other.y),
                (z * other.x) - (x * other.z),
                (x * other.y) - (y * other.x))

        fun dotProduct(other: Vector) = x * other.x + y * other.y + z * other.z

        fun scale(scale: Float) = Vector(x * scale, y * scale, z * scale)
    }
}