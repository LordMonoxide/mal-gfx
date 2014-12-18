package mal.gfx;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import org.lwjgl.opengl.GLContext;

public class ContextBuilder {
  private GLContext _gl;
  private boolean _alpha;
  private boolean _depth;
  
  public ContextBuilder forWindow(Window window) {
    glfwMakeContextCurrent(window.getWindow());
    _gl = GLContext.createFromCurrent();
    return this;
  }
  
  public ContextBuilder withAlpha() {
    _alpha = true;
    return this;
  }
  
  public ContextBuilder withDepth() {
    _depth = true;
    return this;
  }
  
  public Context build() {
    return new Context(_gl, _alpha, _depth);
  }
}