package mal.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.*;
import org.lwjgl.system.libffi.Closure;

public class Window {
  public final WindowEvents events;
  
  private Thread _thread;
  
  long _window;
  
  private List<Closure> _callbacks = new ArrayList<Closure>();
  
  Window(WindowEvents events, int w, int h, String title, boolean visible, boolean resizable) {
    this.events = events;
    
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
    
    events.onCreate();
  }
  
  private void addCallbacks() {
    glfwSetWindowPosCallback(_window, addCallback(new GLFWwindowposfun() {
      @Override public void invoke(long window, int xpos, int ypos) {
        events.onMove(xpos, ypos);
      }
    }));
    
    glfwSetWindowSizeCallback(_window, addCallback(new GLFWwindowsizefun() {
      @Override public void invoke(long window, int width, int height) {
        events.onResize(width, height);
      }
    }));
    
    glfwSetWindowCloseCallback(_window, addCallback(new GLFWwindowclosefun() {
      @Override public void invoke(long window) {
        events.onClose();
      }
    }));
    
    glfwSetWindowRefreshCallback(_window, addCallback(new GLFWwindowrefreshfun() {
      @Override public void invoke(long window) {
        events.onRefresh();
      }
    }));
    
    glfwSetWindowFocusCallback(_window, addCallback(new GLFWwindowfocusfun() {
      @Override public void invoke(long window, int focused) {
        events.onFocus(focused);
      }
    }));
    
    glfwSetWindowIconifyCallback(_window, addCallback(new GLFWwindowiconifyfun() {
      @Override public void invoke(long window, int iconified) {
        events.onIconify(iconified);
      }
    }));
    
    glfwSetKeyCallback(_window, addCallback(new GLFWkeyfun() {
      @Override public void invoke(long window, int key, int scancode, int action, int mods) {
        events.onKeyPress(key, scancode, action, mods);
      }
    }));
    
    glfwSetCharModsCallback(_window, addCallback(new GLFWcharmodsfun() {
      @Override public void invoke(long window, int codepoint, int mods) {
        events.onCharacter(codepoint, mods);
      }
    }));
    
    glfwSetMouseButtonCallback(_window, addCallback(new GLFWmousebuttonfun() {
      @Override public void invoke(long window, int button, int action, int mods) {
        events.onMouseButton(button, action, mods);
      }
    }));
    
    glfwSetCursorPosCallback(_window, addCallback(new GLFWcursorposfun() {
      @Override public void invoke(long window, double xpos, double ypos) {
        events.onMouseMove(xpos, ypos);
      }
    }));
    
    glfwSetCursorEnterCallback(_window, addCallback(new GLFWcursorenterfun() {
      @Override public void invoke(long window, int entered) {
        events.onMouseHover(entered);
      }
    }));
    
    glfwSetScrollCallback(_window, addCallback(new GLFWscrollfun() {
      @Override public void invoke(long window, double xoffset, double yoffset) {
        events.onMouseScroll(xoffset, yoffset);
      }
    }));
    
    glfwSetFramebufferSizeCallback(_window, addCallback(new GLFWframebuffersizefun() {
      @Override public void invoke(long window, int width, int height) {
        events.onBufferResize(width, height);
      }
    }));
    
    glfwSetDropCallback(_window, addCallback(new GLFWdropfun() {
      @Override public void invoke(long window, int count, long names) {
        events.onDrop(count, names);
      }
    }));
  }
  
  private <T extends Closure> T addCallback(T callback) {
    _callbacks.add(callback);
    return callback;
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