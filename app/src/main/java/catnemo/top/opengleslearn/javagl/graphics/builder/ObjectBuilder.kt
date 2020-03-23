package catnemo.top.opengleslearn.javagl.graphics.builder

import android.opengl.GLES20

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2019/04/29
 *
 */
class ObjectBuilder private constructor(sizeInvertices: Int) {

    interface DrawCommand {
        fun draw()
    }

    data class GeneratedData(val vertexData: FloatArray, val drawList: List<DrawCommand>)

    // 顶点数组
    private val vertexData: FloatArray = FloatArray(sizeInvertices * FLOATS_PER_VERTEX)

    // 顶点数组偏移量
    private var offset: Int = 0

    private val drawList = ArrayList<DrawCommand>()

    companion object {
        // 顶点个数
        private const val FLOATS_PER_VERTEX: Int = 3


        private fun sizeOfCircleInVertices(numPoints: Int): Int {
            return 1 + (numPoints + 1)
        }

        private fun sizeOfOpenCylinderInVertices(numPoints: Int): Int {
            return (numPoints + 1) * 2
        }

        fun createCircle(circle: Geometry.Circle, numPoints: Int, axis: String): GeneratedData {
            val size = sizeOfCircleInVertices(numPoints)

            val builder = ObjectBuilder(size)
            builder.appendCircle(circle, numPoints, axis)

            return builder.build()
        }

        fun createCone(cone: Geometry.Conde, numPoints: Int): GeneratedData {
            val size = sizeOfCircleInVertices(numPoints) + sizeOfCircleInVertices(numPoints)
            val topCircle = Geometry.Circle(cone.center, cone.radius)
            val builder = ObjectBuilder(size)
            builder.appendCircle(topCircle, numPoints, "y")
            builder.appendCone(cone, numPoints)

            return builder.build()
        }

        fun createCylinder(cylinder: Geometry.Cylinder, numPoints: Int): GeneratedData {
            val size = (sizeOfCircleInVertices(numPoints) + sizeOfCircleInVertices(numPoints)
                            + sizeOfOpenCylinderInVertices(numPoints))
            val topCircle = Geometry.Circle(cylinder.center.translateY(cylinder.height / 2f), cylinder.radius)
            val bottomCircle = Geometry.Circle(cylinder.center.translateY(-cylinder.height/2), cylinder.radius)
            val builder = ObjectBuilder(size)
            builder.appendCircle(topCircle, numPoints, "y")
            builder.appendCircle(bottomCircle, numPoints, "y")
            builder.appendOpenCylinder(cylinder, numPoints)

            return builder.build()
        }
    }


    private fun build(): GeneratedData {
        return GeneratedData(vertexData, drawList)
    }

    private fun appendCircle(circle: Geometry.Circle, numPoints: Int, axis: String) {

        val startVertex = offset / FLOATS_PER_VERTEX
        val numVertices = sizeOfCircleInVertices(numPoints)
        // 构造圆心
        vertexData[offset++] = circle.center.x
        vertexData[offset++] = circle.center.y
        vertexData[offset++] = circle.center.z

        for (i in 0..numPoints) {
            val angleInRadians = (i.toFloat() / numPoints) * (Math.PI * 2f) // 弧度值
            when (axis) {
                "x" -> {
                    vertexData[offset++] = circle.center.x
                    vertexData[offset++] = (circle.center.y + circle.radius * Math.cos(angleInRadians)).toFloat()
                    vertexData[offset++] = (circle.center.z + circle.radius * Math.sin(angleInRadians)).toFloat()
                }
                "y" -> {
                    vertexData[offset++] = (circle.center.x + circle.radius * Math.cos(angleInRadians)).toFloat()
                    vertexData[offset++] = circle.center.y
                    vertexData[offset++] = (circle.center.z + circle.radius * Math.sin(angleInRadians)).toFloat()
                }
                "z" -> {
                    vertexData[offset++] = (circle.center.x + circle.radius * Math.cos(angleInRadians)).toFloat()
                    vertexData[offset++] = (circle.center.y + circle.radius * Math.sin(angleInRadians)).toFloat()
                    vertexData[offset++] = circle.center.z
                }
            }

        }

        drawList.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices)
            }
        })
    }

    private fun appendCone(cone: Geometry.Conde, numPoints: Int) {

        val startVertex = offset / FLOATS_PER_VERTEX
        val numVertices = sizeOfCircleInVertices(numPoints)
        // 构造圆心
        vertexData[offset++] = cone.center.x
        vertexData[offset++] = cone.height
        vertexData[offset++] = cone.center.z

        for (i in 0..numPoints) {
            val angleInRadians = (i.toFloat() / numPoints) * (Math.PI * 2f) // 弧度值

            vertexData[offset++] = (cone.center.x + cone.radius * Math.cos(angleInRadians)).toFloat()
            vertexData[offset++] = cone.center.y
            vertexData[offset++] = (cone.center.z + cone.radius * Math.sin(angleInRadians)).toFloat()

        }

        drawList.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices)
            }
        })
    }

    private fun appendOpenCylinder(cylinder: Geometry.Cylinder, numPoints: Int) {

        val startVertex = offset / FLOATS_PER_VERTEX
        val numVertices = sizeOfOpenCylinderInVertices(numPoints)
        val yStart = cylinder.center.y - cylinder.height / 2f
        val yEnd = cylinder.center.y + cylinder.height / 2f
        for (i in 0..numPoints) {
            val angleInRadians = (i.toFloat() / numPoints) * (Math.PI * 2f) // 弧度值
            val xPosition = (cylinder.center.x + cylinder.radius * Math.cos(angleInRadians)).toFloat()
            val zPosition = (cylinder.center.z + cylinder.radius * Math.sin(angleInRadians)).toFloat()
            vertexData[offset++] = xPosition
            vertexData[offset++] = yStart
            vertexData[offset++] = zPosition

            vertexData[offset++] = xPosition
            vertexData[offset++] = yEnd
            vertexData[offset++] = zPosition

        }

        drawList.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices)
            }
        })
    }
}