package mal.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.*;
import org.lwjgl.system.libffi.Closure;

public class Window {
  public final WindowEvents events;
  
  private Thread _thread;
  private int _w, _h;
  
  long _window;
  
  private List<Closure> _callbacks = new ArrayList<>();
  
  Window(WindowEvents events, int w, int h, String title, boolean visible, boolean resizable) {
    this.events = events;
    
    _w = w;
    _h = h;
    
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
  
  public void destroy() {
    for(Closure callback : _callbacks) {
      callback.release();
    }
  }
  
  long getWindow() {
    return _window;
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
    glfwSetWindowPosCallback(_window, addCallback(new GLFWWindowPosCallback() {
      @Override public void invoke(long window, int xpos, int ypos) {
        events.onMove(xpos, ypos);
      }
    }));
    
    glfwSetWindowSizeCallback(_window, addCallback(new GLFWWindowSizeCallback() {
      @Override public void invoke(long window, int width, int height) {
        _w = width;
        _h = height;
        events.onResize(width, height);
      }
    }));
    
    glfwSetWindowCloseCallback(_window, addCallback(new GLFWWindowCloseCallback() {
      @Override public void invoke(long window) {
        events.onClose();
      }
    }));
    
    glfwSetWindowRefreshCallback(_window, addCallback(new GLFWWindowRefreshCallback() {
      @Override public void invoke(long window) {
        events.onRefresh();
      }
    }));
    
    glfwSetWindowFocusCallback(_window, addCallback(new GLFWWindowFocusCallback() {
      @Override public void invoke(long window, int focused) {
        events.onFocus(focused);
      }
    }));
    
    glfwSetWindowIconifyCallback(_window, addCallback(new GLFWWindowIconifyCallback() {
      @Override public void invoke(long window, int iconified) {
        events.onIconify(iconified);
      }
    }));
    
    glfwSetKeyCallback(_window, addCallback(new GLFWKeyCallback() {
      @Override public void invoke(long window, int key, int scancode, int action, int mods) {
        events.onKeyPress(key, scancode, action, mods);
      }
    }));
    
    glfwSetCharModsCallback(_window, addCallback(new GLFWCharModsCallback() {
      @Override public void invoke(long window, int codepoint, int mods) {
        events.onCharacter(codepoint, mods);
      }
    }));
    
    glfwSetMouseButtonCallback(_window, addCallback(new GLFWMouseButtonCallback() {
      @Override public void invoke(long window, int button, int action, int mods) {
        events.onMouseButton(button, action, mods);
      }
    }));
    
    glfwSetCursorPosCallback(_window, addCallback(new GLFWCursorPosCallback() {
      @Override public void invoke(long window, double xpos, double ypos) {
        events.onMouseMove(xpos, ypos);
      }
    }));
    
    glfwSetCursorEnterCallback(_window, addCallback(new GLFWCursorEnterCallback() {
      @Override public void invoke(long window, int entered) {
        events.onMouseHover(entered);
      }
    }));
    
    glfwSetScrollCallback(_window, addCallback(new GLFWScrollCallback() {
      @Override public void invoke(long window, double xoffset, double yoffset) {
        events.onMouseScroll(xoffset, yoffset);
      }
    }));
    
    glfwSetFramebufferSizeCallback(_window, addCallback(new GLFWFramebufferSizeCallback() {
      @Override public void invoke(long window, int width, int height) {
        events.onBufferResize(width, height);
      }
    }));
    
    glfwSetDropCallback(_window, addCallback(new GLFWDropCallback() {
      @Override public void invoke(long window, int count, long names) {
        events.onDrop(count, names);
      }
    }));
  }
  
  private <T extends Closure> T addCallback(T callback) {
    _callbacks.add(callback);
    return callback;
  }
  
  public int getW() {
    return _w;
  }
  
  public int getH() {
    return _h;
  }
  
  private void loop() {
    while(glfwWindowShouldClose(_window) == GL_FALSE) {
      events.onLoop();
      
      glfwSwapBuffers(_window);
      glfwPollEvents();
    }
    
    glfwDestroyWindow(_window);
  }
}