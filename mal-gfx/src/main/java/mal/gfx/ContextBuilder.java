package mal.gfx;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import org.lwjgl.opengl.GLContext;

public class ContextBuilder {
  private GLContext _gl;
  private boolean _alpha;
  private boolean _depth;
  private int _w, _h;
  
  public ContextBuilder forWindow(Window window) {
    glfwMakeContextCurrent(window.getWindow());
    _gl = GLContext.createFromCurrent();
    _w = window.getW();
    _h = window.getH();
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
  
  public ContextBuilder withSize(int w, int h) {
    _w = w;
    _h = h;
    return this;
  }
  
  public Context build() {
    return new Context(_gl, _alpha, _depth, _w, _h);
  }
}