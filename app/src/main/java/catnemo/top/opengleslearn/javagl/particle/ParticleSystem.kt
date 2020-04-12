package catnemo.top.opengleslearn.javagl.particle

import android.graphics.BitmapFactory
import android.graphics.Color
import android.opengl.GLES20
import android.opengl.GLUtils
import android.opengl.Matrix
import catnemo.top.opengleslearn.App
import catnemo.top.opengleslearn.R
import catnemo.top.opengleslearn.javagl.gles.utils.TextureHelper
import catnemo.top.opengleslearn.javagl.graphics.Graphics
import catnemo.top.opengleslearn.javagl.graphics.VertexArray
import catnemo.top.opengleslearn.javagl.graphics.builder.Geometry

class ParticleSystem(val maxParticleCount: Int) : Graphics<ParticleShaderProgram>() {

    companion object {
        const val POSITION_COMPONENT_COUNT = 3
        const val COLOR_COMPONENT_COUNT = 3
        const val VECTOR_COMPONENT_COUNT = 3
        const val PARTICLE_START_TIME__COMPONENT_COUNT = 1

        const val TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT +
                COLOR_COMPONENT_COUNT +
                VECTOR_COMPONENT_COUNT +
                PARTICLE_START_TIME__COMPONENT_COUNT
        const val STRIDE = TOTAL_COMPONENT_COUNT * PER_FLOAT_BYTE
    }

    // 最大粒子个数数组
    private val particles = FloatArray(maxParticleCount * TOTAL_COMPONENT_COUNT)
    private val vertexArray = VertexArray(particles)

    private var currentParticleCount = 0
    private var nextParticle = 0
    override val program: ParticleShaderProgram = ParticleShaderProgram()

    private var globalStartTime = 0L

    private var redParticleShooter: ParticleShooter? = null
    private var greenParticleShooter: ParticleShooter? = null
    private var blueParticleShooter: ParticleShooter? = null

    private val projectionMtx = FloatArray(16)
    private val viewProjectionMatrix = FloatArray(16)
    private val viewMtx = FloatArray(16)

    private val angleVarianceInDegress = 5f
    private val speedVariance = 1f

    private var textureId = -1


    fun addParticle(position: Geometry.Point, color: Int, direction: Geometry.Vector, particleStartTime: Float) {
        val particleOffset = nextParticle * TOTAL_COMPONENT_COUNT
        var currentOffset = particleOffset
        nextParticle++
        if (currentParticleCount < maxParticleCount) {
            currentParticleCount++
        }
        if (nextParticle == maxParticleCount) {
            nextParticle = 0
        }
        particles[currentOffset++] = position.x
        particles[currentOffset++] = position.y
        particles[currentOffset++] = position.z

        particles[currentOffset++] = Color.red(color) / 255f
        particles[currentOffset++] = Color.green(color) / 255f
        particles[currentOffset++] = Color.blue(color) / 255f

        particles[currentOffset++] = direction.x
        particles[currentOffset++] = direction.y
        particles[currentOffset++] = direction.z


        particles[currentOffset++] = particleStartTime

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT)
    }

    override fun onInited() {

        globalStartTime = System.nanoTime()

        val particleDirection = Geometry.Vector(0.0f, 0.5f, 0.0f)
        redParticleShooter = ParticleShooter(Geometry.Point(-1f, 0f, 0f), particleDirection,
                Color.rgb(255, 50, 5), angleVarianceInDegress, speedVariance)

        greenParticleShooter = ParticleShooter(Geometry.Point(0f, 0f, 0f), particleDirection,
                Color.rgb(25, 255, 25), angleVarianceInDegress, speedVariance)

        blueParticleShooter = ParticleShooter(Geometry.Point(1f, 0f, 0f), particleDirection,
                Color.rgb(5, 50, 255), angleVarianceInDegress, speedVariance)

        Matrix.perspectiveM(projectionMtx, 0, 45f, (mSurfaceWidth * 1.0f / mSurfaceHeight),
                1f, 10f)
        Matrix.setIdentityM(viewMtx, 0)
        Matrix.translateM(viewMtx, 0, 0f, -1.5f, -5f)
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMtx, 0, viewMtx, 0)

    }

    override fun onPreDraw(viewMtx: FloatArray) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        var dataOffset = 0
        vertexArray.setVertexAttribPointer(dataOffset, program.aPositionLocation, POSITION_COMPONENT_COUNT, STRIDE)
        dataOffset += POSITION_COMPONENT_COUNT
        vertexArray.setVertexAttribPointer(dataOffset, program.aColorLocation, COLOR_COMPONENT_COUNT, STRIDE)
        dataOffset += COLOR_COMPONENT_COUNT

        vertexArray.setVertexAttribPointer(dataOffset, program.aDirectionVectorLocation, VECTOR_COMPONENT_COUNT, STRIDE)
        dataOffset += VECTOR_COMPONENT_COUNT

        vertexArray.setVertexAttribPointer(dataOffset, program.aParticeStartTimeLocation, PARTICLE_START_TIME__COMPONENT_COUNT, STRIDE)


        val currentTime = (System.nanoTime() - globalStartTime) / (1000 * 1000 * 1000f)



        redParticleShooter?.addParticles(this, currentTime, 5)
        greenParticleShooter?.addParticles(this, currentTime, 5)
        blueParticleShooter?.addParticles(this, currentTime, 5)

        program.setUniFormMat4(program.uMatrixLocation, viewProjectionMatrix)
        program.setUniForm1f(program.uTimeLocation, currentTime)

        if (textureId == -1) {
            bindTexture()
        }
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glUniform1i(program.uTextureLocation, 0)

    }

    private fun bindTexture() {
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(App.mContext.resources,
                R.drawable.particle_texture, options)

        textureId = TextureHelper.createTexture2D()
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        GLES20.glUniform1i(program.uTextureLocation, 0)

        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }

    override fun drawCommand() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount)
        GLES20.glDisable(GLES20.GL_BLEND)
        GLES20.glDisableVertexAttribArray(program.aPositionLocation)
        GLES20.glDisableVertexAttribArray(program.aColorLocation)
        GLES20.glDisableVertexAttribArray(program.aDirectionVectorLocation)
        GLES20.glDisableVertexAttribArray(program.aParticeStartTimeLocation)

    }

    override fun onReleased() {
        program.release()
    }

}