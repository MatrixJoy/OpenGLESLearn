attribute vec4 a_Position; // vec4 x,y,z 3D distance w

// shader 入口
void main(){
    // 分配最终坐标 给当前顶点
    gl_Position = a_Position;
}