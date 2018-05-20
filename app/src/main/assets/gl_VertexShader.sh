//计算变换矩阵坐标
uniform mat4 uMVPMatrix;
//输入顶点着色器的attribute
//位置值
attribute vec4 aPosition;
//输入的顶点颜色
attribute vec2 aTexCoor;
//输出的顶点颜色
varying vec2 vTextureCoord;
void main()
{
  gl_Position = uMVPMatrix * aPosition;
  vTextureCoord =  aTexCoor;
}