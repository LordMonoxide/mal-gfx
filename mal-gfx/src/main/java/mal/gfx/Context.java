package mal.gfx;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Context {
  private final GLContext _gl;
  
  public final TextureLoader textures = new TextureLoader();
  
  private int _clear_mode = GL11.GL_COLOR_BUFFER_BIT;
  
  public Context(GLContext gl, boolean alpha, boolean depth) {
    _gl = gl;
    
    if(alpha) {
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    if(depth) {
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      _clear_mode |=  GL11.GL_DEPTH_BUFFER_BIT;
    }
  }
  
  public void clear() {
    GL11.glClear(_clear_mode);
  }
}