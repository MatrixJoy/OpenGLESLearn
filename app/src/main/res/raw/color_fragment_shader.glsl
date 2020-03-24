precision mediump float; // lowp mediump highp(只支持部分实现) 三种精度

varying vec4 v_Color;  // 接收顶点着色器传递过来的颜色
void main(){

    gl_FragColor = v_Color;

}