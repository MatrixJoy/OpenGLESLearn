precision mediump float; // lowp mediump highp(只支持部分实现) 三种精度


varying vec2 v_uv; // 顶点坐标
uniform sampler2D texture; // 纹理数据

void main(){
    gl_FragColor = texture2D(texture, v_uv);

}