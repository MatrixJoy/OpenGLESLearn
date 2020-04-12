package catnemo.top.opengleslearn.javagl.particle

import android.opengl.GLES20
import catnemo.top.opengleslearn.R
import catnemo.top.opengleslearn.javagl.gles.program.BaseShaderProgram

class ParticleShaderProgram :
        BaseShaderProgram(R.raw.particle_vertex_shader, R.raw.particle_fragment_shader) {

    var uTimeLocation = 0
        private set
    var uTextureLocation = 0
        private set
    var aColorLocation = 0
        private set
    var aDirectionVectorLocation = 0
        private set
    var aParticeStartTimeLocation = 0
        private set

    companion object {
        const val U_MATRIX = "u_Matrix"
        const val U_TIME = "u_Time"
        const val U_TEXTUREUNIT = "u_TextureUnit"

        const val A_POSITION = "a_Position";// vec4 x,y,z 3D distance w
        const val A_COLOR = "a_Color";
        const val A_DIRECTIONVECTOR = "a_DirectionVector"
        const val A_PARTICESTARTTIME = "a_ParticeStartTime"
    }

    override fun onInit() {
        // uniform
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX)
        uTimeLocation = GLES20.glGetUniformLocation(mProgram, U_TIME)
        uTextureLocation = GLES20.glGetUniformLocation(mProgram, U_TEXTUREUNIT)

        // attribute
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        aColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR)
        aDirectionVectorLocation = GLES20.glGetAttribLocation(mProgram, A_DIRECTIONVECTOR)
        aParticeStartTimeLocation = GLES20.glGetAttribLocation(mProgram, A_PARTICESTARTTIME)
    }


}