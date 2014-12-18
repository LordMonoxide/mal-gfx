package mal.gfx;

import java.util.Stack;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MatrixStack {
  public static Matrix4f identity() {
    Matrix4f mat = new Matrix4f();
    mat.m00 = 1;
    mat.m11 = 1;
    mat.m22 = 1;
    mat.m33 = 1;
    return mat;
  }
  
  public static Matrix4f ortho(float l, float r, float t, float b, float n, float f) {
    Matrix4f mat = new Matrix4f();
    mat.m00 =  2 / (r - l);
    mat.m11 =  2 / (t - b);
    mat.m22 = -2 / (f - n);
    mat.m30 = -(r + l) / (r - l);
    mat.m31 = -(t + b) / (t - b);
    mat.m32 = -(f + n) / (f - n);
    mat.m33 = 1;
    return mat;
  }
  
  private final Stack<Matrix4f> _stack = new Stack<>();
  
  private Matrix4f _proj;
  private Matrix4f _top;
  
  public MatrixStack() {
    _top = identity();
  }
  
  public void setProjection(Matrix4f projection) {
    _proj = projection;
  }
  
  public Matrix4f getProjection() {
    return _proj;
  }
  
  public Matrix4f getView() {
    return _top;
  }
  
  public void translate(float x, float y) {
    _top.translate(new Vector2f(x, y));
  }
  
  public void translate(float x, float y, float z) {
    _top.translate(new Vector3f(x, y, z));
  }
}