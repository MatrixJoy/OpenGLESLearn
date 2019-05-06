attribute vec4 a_Position; // vec4 x,y,z 3D distance w
attribute vec4 a_Color;  // 接收 外部传进来的值
uniform mat4 u_Matrix;
varying vec4 v_Color; // 混合颜色 传递给 片源着色器使用

// shader 入口
void main(){
    v_Color = a_Color;
    // 分配最终坐标 给当前顶点
    gl_Position = u_Matrix * a_Position;
}