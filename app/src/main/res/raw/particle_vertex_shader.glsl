uniform mat4 u_Matrix;
uniform float u_Time;

attribute vec3 a_Position;// vec4 x,y,z 3D distance w
attribute vec3 a_Color;
attribute vec3 a_DirectionVector;
attribute float a_ParticeStartTime;

varying vec3 v_Color;
varying float v_ElapsedTime;

// shader 入口
void main(){
    v_Color = a_Color;
    v_ElapsedTime = u_Time - a_ParticeStartTime;
    float gravityFactor = v_ElapsedTime*v_ElapsedTime/9.8;
    vec3 currentPosition = a_Position+(a_DirectionVector*v_ElapsedTime);
    currentPosition.y-=gravityFactor;
    gl_Position = u_Matrix *vec4(currentPosition, 1.0);
    gl_PointSize = 25.0;
}