package mal.gfx;

public abstract class Drawable {
  public Drawable(float[] vertices, byte[] indices) {
    create(vertices, indices);
  }
  
  protected abstract void create(float[] vertices, byte[] indices);
  
  public abstract void destroy();
  public abstract void draw();
}