package mal.gfx;

public abstract class TexturedDrawable {
  private Texture _texture;
  
  public TexturedDrawable(float[] vertices, Texture texture) {
    _texture = texture;
  }
  
  public TexturedDrawable(float[] vertices, byte[] indices, Texture texture) {
    _texture = texture;
  }
  
  public Texture getTexture() {
    return _texture;
  }
  
  public void setTexture(Texture texture) {
    _texture = texture;
  }
  
  public abstract void destroy();
  public abstract void draw();
}