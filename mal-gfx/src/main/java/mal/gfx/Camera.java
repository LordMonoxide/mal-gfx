package mal.gfx;

import org.lwjgl.util.vector.Vector3f;

public class Camera {
  private final Vector3f _eye    = new Vector3f(0, 0, 100);
  private final Vector3f _center = new Vector3f(0, 0, 0);
  private final Vector3f _up     = new Vector3f(0, 1, 0);
  
  public final MatrixStack proj = new MatrixStack();
  public final MatrixStack view = new MatrixStack();
  
  public void reset() {
    view.lookAt(_eye, _center, _up);
  }
}