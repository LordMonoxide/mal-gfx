package mal.gfx;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
  private final Vector3f _up = new Vector3f(0, 1, 0);
  
  public final FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
  public final FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16);
  
  private Matrix4f _proj;
  private Matrix4f _view;
  
  public void setProjection(Matrix4f proj) {
    _proj = proj;
    updateProjBuffer();
  }
  
  public void lookAt(Vector3f eye, Vector3f center) {
    _view = MatrixStack.lookAt(eye, center, _up);
    updateViewBuffer();
  }
  
  private void updateProjBuffer() {
    _proj.store(projBuffer);
    projBuffer.flip();
  }
  
  private void updateViewBuffer() {
    _view.store(viewBuffer);
    viewBuffer.flip();
  }
}