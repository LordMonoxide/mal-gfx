package mal.gfx;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GLContext;

public class Context {
  private final GLContext _gl;
  
  public Context(Window window) {
    glfwMakeContextCurrent(window.getWindow());
    _gl = GLContext.createFromCurrent();
  }
}