#extension GL_OES_EGL_image_external : require
//精度设置
precision mediump float;
//vertex输出的纹理坐标
//varying vec2 vTextureCoord;
uniform vec4 uColor;
void main() 
{
  gl_FragColor = uColor;
}