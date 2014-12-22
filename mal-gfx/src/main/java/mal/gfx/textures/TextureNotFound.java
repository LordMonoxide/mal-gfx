package mal.gfx.textures;

public class TextureNotFound extends Texture {
  public TextureNotFound() {
    super(0, 0);
  }

  @Override protected int generateID() {
    return 0;
  }

  @Override public void use() {
    
  }
  
  @Override public void destroy() {
    
  }
}