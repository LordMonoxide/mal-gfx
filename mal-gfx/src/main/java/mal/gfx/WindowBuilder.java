package mal.gfx;

import static org.lwjgl.system.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.glfw.ErrorCallback;

public class WindowBuilder {
  static {
    glfwSetErrorCallback(ErrorCallback.Util.getDefault());
    
    if(glfwInit() != GL11.GL_TRUE) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }
  }
  
  public static void main(String[] args) {
    Window window = new WindowBuilder()
      .title("Malachite").build();
  }
  
  private int _w = 1280;
  private int _h =  720;
  private String _title = "Window";
  private boolean _visible = true;
  private boolean _resizable = false;
  
  public WindowBuilder size(int w, int h) {
    _w = w;
    _h = h;
    return this;
  }
  
  public WindowBuilder title(String title) {
    _title = title;
    return this;
  }
  
  public WindowBuilder visible(boolean visible) {
    _visible = visible;
    return this;
  }
  
  public WindowBuilder resizable(boolean resizable) {
    _resizable = resizable;
    return this;
  }
  
  public Window build() {
    return new Window(_w, _h, _title, _visible, _resizable);
  }
}