package mal.gfx;

public abstract class Drawable {
  private Texture _texture;
  
  public Drawable(float[] vertices, byte[] indices) {
    create(vertices, indices);
  }
  
  public Drawable(float[] vertices, byte[] indices, Texture texture) {
    this(vertices, indices);
    setTexture(texture);
  }
  
  public Texture getTexture() {
    return _texture;
  }
  
  public void setTexture(Texture texture) {
    _texture = texture;
  }
  
  protected abstract void create(float[] vertices, byte[] indices);
  
  public abstract void destroy();
  public abstract void draw();
}