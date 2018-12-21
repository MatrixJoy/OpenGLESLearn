precision mediump float; // lowp mediump highp(只支持部分实现) 三种精度

uniform vec4 u_Color; // r,g,b,a

void main(){

    gl_FragColor = u_Color;

}