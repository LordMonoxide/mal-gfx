package mal.gfx;

import java.nio.FloatBuffer;
import java.util.Stack;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MatrixStack {
  private final Stack<Matrix4f> _stack = new Stack<>();
  
  private Matrix4f _top;
  
  private final FloatBuffer _buffer = BufferUtils.createFloatBuffer(16);
  private boolean _updated = true;
  
  public MatrixStack() {
    _top = Matrices.identity();
  }
  
  public FloatBuffer getBuffer() {
    if(_updated) {
      _top.store(_buffer);
      _buffer.flip();
      _updated = false;
    }
    
    return _buffer;
  }
  
  public void push() {
    _stack.push(_top);
    _top = new Matrix4f(_top);
  }
  
  public void pop() {
    _top = _stack.pop();
  }
  
  public void push(Runnable r) {
    push();
    r.run();
    pop();
  }
  
  public void set(Matrix4f matrix) {
    _top = matrix;
    _updated = true;
  }
  
  public void reset() {
    set(Matrices.identity());
  }
  
  public void lookAt(Vector3f eye, Vector3f center, Vector3f up) {
    set(Matrices.lookAt(eye, center, up));
  }
  
  public void translate(Vector3f vec) {
    _top.translate(vec);
    _updated = true;
  }
}