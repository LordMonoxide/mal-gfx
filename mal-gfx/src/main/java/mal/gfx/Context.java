package mal.gfx;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Context {
  private final GLContext _gl;
  
  public final TextureLoader textures = new TextureLoader();
  
  public Context(Window window) {
    glfwMakeContextCurrent(window.getWindow());
    _gl = GLContext.createFromCurrent();
    
    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
  }
}