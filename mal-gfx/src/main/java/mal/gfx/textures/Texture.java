package mal.gfx.textures;

import org.lwjgl.util.vector.Vector2f;

public abstract class Texture {
  public final Vector2f size = new Vector2f();
  
  public abstract void use();
  public abstract void destroy();
}