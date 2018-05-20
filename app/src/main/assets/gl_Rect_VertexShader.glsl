//位置值
attribute vec4 aPosition;
//输入的顶点颜色
//attribute vec2 aTexCoor;
//输出的顶点颜色
//varying vec2 vTextureCoord;
void main()
{
  gl_Position = aPosition;
//  vTextureCoord =  aTexCoor;
}