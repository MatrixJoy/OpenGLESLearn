package catnemo.top.opengleslearn.entity

import catnemo.top.opengleslearn.javagl.gles.program.BaseShaderProgram
import catnemo.top.opengleslearn.javagl.graphics.Graphics

/**
 *
 * @author zhoujunjiang
 * @version V1.0
 * @since 2018/12/19
 *
 */
data class Item(val name: String, val  shape: Graphics<out BaseShaderProgram>)