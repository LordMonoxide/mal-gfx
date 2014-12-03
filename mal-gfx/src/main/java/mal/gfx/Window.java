package mal.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.system.glfw.GLFWvidmode;
import org.lwjgl.system.glfw.WindowCallback;

public class Window {
  public final WindowEvents events = new WindowEvents();
  
  private Thread _thread;
  
  private long _window;
  
  Window(int w, int h, String title, boolean visible, boolean resizable) {
    _thread = new Thread(() -> {
      createWindow(w, h, title, visible, resizable);
      addCallbacks();
      
      ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      
      glfwSetWindowPos(
        _window,
        (GLFWvidmode.width(vidmode)  - w) / 2,
        (GLFWvidmode.height(vidmode) - h) / 2
      );
      
      loop();
    });
    
    _thread.start();
  }
  
  private void createWindow(int w, int h, String title, boolean visible, boolean resizable) {
    glfwWindowHint(GLFW_VISIBLE,   visible   ? GL_TRUE : GL_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE);
    
    _window = glfwCreateWindow(w, h, title, NULL, NULL);
    
    if(_window == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }
    
    glfwMakeContextCurrent(_window);
    glfwSwapInterval(1);
    
    glfwShowWindow(_window);
  }
  
  private void addCallbacks() {
    WindowCallback.set(_window, new WindowCallback() {
      @Override public void windowPos(long window, int xpos, int ypos) {
        events.onMove(xpos, ypos);
      }
      
      @Override public void windowSize(long window, int width, int height) {
        events.onResize(width, height);
      }
      
      @Override public void windowClose(long window) {
        events.onClose();
      }
      
      @Override public void windowRefresh(long window) {
        events.onRefresh();
      }
      
      @Override public void windowFocus(long window, int focused) {
        events.onFocus(focused);
      }
      
      @Override public void windowIconify(long window, int iconified) {
        events.onIconify(iconified);
      }
      
      @Override public void key(long window, int key, int scancode, int action, int mods) {
        events.onKeyPress(key, scancode, action, mods);
      }
      
      @Override public void character(long window, int codepoint) { }
      @Override public void charMods(long window, int codepoint, int mods) {
        events.onCharacter(codepoint, mods);
      }
      
      @Override public void mouseButton(long window, int button, int action, int mods) {
        events.onMouseButton(button, action, mods);
      }
      
      @Override public void cursorPos(long window, double xpos, double ypos) {
        events.onMouseMove(xpos, ypos);
      }
      
      @Override public void cursorEnter(long window, int entered) {
        events.onMouseHover(entered);
      }
      
      @Override public void scroll(long window, double xoffset, double yoffset) {
        events.onMouseScroll(xoffset, yoffset);
      }
      
      @Override public void framebufferSize(long window, int width, int height) {
        events.onBufferResize(width, height);
      }
      
      @Override public void drop(long window, int count, long names) {
        events.onDrop(count, names);
      }
    });
  }
  
  private void loop() {
    while(glfwWindowShouldClose(_window) == GL_FALSE) {
      glfwSwapBuffers(_window);
      glfwPollEvents();
    }
  }
}