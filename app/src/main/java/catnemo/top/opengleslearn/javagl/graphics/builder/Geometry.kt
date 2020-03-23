package catnemo.top.opengleslearn.javagl.graphics.builder

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
}