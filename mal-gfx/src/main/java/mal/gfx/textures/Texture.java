package mal.gfx.textures;

import org.lwjgl.util.vector.Vector2f;

public abstract class Texture {
  public final int id;
  
  public final Vector2f size = new Vector2f();
  
  public Texture(int w, int h) {
    id = generateID();
    
    size.x = w;
    size.y = h;
  }
  
  protected abstract int generateID();
  
  public abstract void use();
  public abstract void destroy();
}