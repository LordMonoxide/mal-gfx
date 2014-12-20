package mal.gfx;

import java.nio.FloatBuffer;
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
  
  public static Matrix4f perspective(float l, float r, float t, float b, float n, float f) {
    Matrix4f mat = new Matrix4f();
    mat.m00 = 2 * n / (r - l);
    mat.m11 = 2 * n / (t - b);
    mat.m22 = -(f + n) / (f - n);
    mat.m20 = (r + l) / (r - l);
    mat.m21 = (t + b) / (t - b);
    mat.m32 = -2 * f * n / (f - n);
    mat.m23 = -1;
    return mat;
  }
  
  public static Matrix4f symmetricPerspective(float w, float h, float n, float f) {
    Matrix4f mat = new Matrix4f();
    mat.m00 = 1 / w;
    mat.m11 = -1 / h;
    mat.m22 = -(f + n) / (f - n);
    mat.m32 = -2 * f * n / (f - n);
    mat.m23 = -1;
    System.out.println(mat.m00);
    System.out.println(mat.m11);
    System.out.println(mat.m22);
    System.out.println(mat.m32);
    return mat;
  }
  
  public static Matrix4f perspectiveFOV(float fov, float aspect, float n, float f) {
    float tanHalfFov = (float)Math.tan(fov / 2);
    
    Matrix4f mat = new Matrix4f();
    mat.m00 = 1 / (aspect * tanHalfFov);
    mat.m11 = 1 / (tanHalfFov);
    mat.m22 = -(f + n) / (f - n);
    mat.m23 = -1;
    mat.m32 = -(2 * f * n) / (f - n);
    System.out.println(mat.m00);
    System.out.println(mat.m11);
    System.out.println(mat.m22);
    System.out.println(mat.m32);
    return mat;
  }
  
  public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
    Vector3f f = new Vector3f();
    Vector3f u = new Vector3f();
    Vector3f s = new Vector3f();
    
    Vector3f.sub(center, eye, f);
    f.normalise(f);
    
    up.normalise(u);
    
    Vector3f.cross(f, u, s);
    s.normalise(s);
    
    Vector3f.cross(s, f, u);
    
    Matrix4f result = new Matrix4f();
    result.m00 =  s.x;
    result.m10 =  s.y;
    result.m20 =  s.z;
    result.m01 =  u.x;
    result.m11 =  u.y;
    result.m21 =  u.z;
    result.m02 = -f.x;
    result.m12 = -f.y;
    result.m22 = -f.z;
    
    return result.translate(new Vector3f(-eye.x, -eye.y, -eye.z));
  }
  
  private final Stack<Matrix4f> _stack = new Stack<>();
  
  private Matrix4f _top;
  
  private FloatBuffer _viewBuffer;
  private boolean _viewUpdated = true;
  
  public MatrixStack() {
    _top = identity();
  }
  
  public FloatBuffer getViewBuffer() {
    if(_viewUpdated) {
      _viewBuffer = Buffers.of(_top);
      _viewUpdated = false;
    }
    
    return _viewBuffer;
  }
  
  public Matrix4f getView() {
    return _top;
  }
  
  public void translate(float x, float y) {
    _top.translate(new Vector2f(x, y));
    _viewUpdated = true;
  }
  
  public void translate(float x, float y, float z) {
    _top.translate(new Vector3f(x, y, z));
    _viewUpdated = true;
  }
}