#extension GL_OES_EGL_image_external : require
//精度设置
precision mediump float;
//输出的纹理坐标
varying vec2 vTextureCoord;
uniform samplerExternalOES sTexture;
void main() 
{
  gl_FragColor = texture2D(sTexture, vTextureCoord);
}