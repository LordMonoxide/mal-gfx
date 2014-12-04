package mal.gfx;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;

import org.lwjgl.glfw.GLFWerrorfun;
import org.lwjgl.opengl.GL11;

public class WindowBuilder {
  static {
    GLFWerrorfun err = errorfunPrint(System.err);
    glfwSetErrorCallback(err);
    
    if(glfwInit() != GL11.GL_TRUE) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }
    
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      err.release();
    }));
  }
  
  public static void main(String[] args) {
    class WC {
      Window window;
      Context context;
      Drawable d;
    }
    
    WC wc = new WC();
    
    wc.window = new WindowBuilder()
      .events(events -> {
        events.onCreate(() -> {
          wc.context = new ContextBuilder()
            .window(wc.window).build();
          
          wc.d = new DrawableVBO(
            new float[] {
              -0.5f,  0.5f, 0f,
              -0.5f, -0.5f, 0f,
               0.5f, -0.5f, 0f,
               0.5f,  0.5f, 0f,
            }, new byte[] {
              0, 1, 2,
              2, 3, 0
            }
          );
        }).onLoop(() -> {
          wc.d.draw();
        });
      }).title("Malachite").build();
  }
  
  private final WindowEvents _events = new WindowEvents();
  private int _w = 1280;
  private int _h =  720;
  private String _title = "Window";
  private boolean _visible = true;
  private boolean _resizable = false;
  
  public WindowBuilder events(WindowBuilderEventCallback callback) {
    callback.run(_events);
    return this;
  }
  
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
    return new Window(_events, _w, _h, _title, _visible, _resizable);
  }
  
  public interface WindowBuilderEventCallback {
    public void run(WindowEvents events);
  }
}