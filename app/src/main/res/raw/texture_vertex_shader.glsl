attribute vec4 a_Position; // vec4 x,y,z 3D distance w
attribute vec2 a_uv; // texture 输入源
varying vec2 v_uv;
uniform mat4 uMVPMatrix;

// shader 入口
void main(){
    v_uv = a_uv;
    // 分配最终坐标 给当前顶点
    gl_Position = uMVPMatrix * a_Position;
}