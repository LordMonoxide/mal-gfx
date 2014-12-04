package mal.gfx;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GLContext;

public class Context {
  public Context(Window window) {
    glfwMakeContextCurrent(window.getWindow());
    GLContext.createFromCurrent();
  }
}