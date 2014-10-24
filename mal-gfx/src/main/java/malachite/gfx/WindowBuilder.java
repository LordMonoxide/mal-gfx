package malachite.gfx;

import java.util.Objects;

import malachite.gfx.ShaderBuilder.VARIABLE_MODE;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Factory class for building {@link Window}s.
 * All parameters are given sensible defaults, so you are
 * not required to set anything other than the title.
 * @author Corey Frenette
 */
public class WindowBuilder {
  public static void main(String[] args) {
    Window window = null;
    
    try {
      window = new WindowBuilder()
        .withTitle("Test").build();
    } catch(LWJGLException e) {
      logger.error("Error while building window", e); //$NON-NLS-1$
    }
    
    Shader s = new ShaderBuilder(window.ctx)
      .variable(VARIABLE_MODE.IN,   "vec4", "pos")
      .variable(VARIABLE_MODE.PASS, "vec4", "col")
      
      .function("void", "main")
        .raw("gl_Position=in_Pos;")
        .raw("pass_Col=in_Col;")
      .build()
    .build();
  }
  
  private static final Logger logger = LoggerFactory.getLogger(WindowBuilder.class);
  
  private ContextBuilder _builder;
  
  private String _title;
  private boolean _resizable = true;
  private int _w = 1280, _h = 720;
  private final float[] _clear = new float[] {0, 0, 0};
  
  /**
   * Sets the {@link ContextBuilder} to be used to construct the {@link Context} used by this {@link Window}.
   * @param builder a non-null instance of the {@link ContextBuilder} class
   * @return this
   */
  public WindowBuilder withContextBuilder(ContextBuilder builder) {
    _builder = Objects.requireNonNull(builder);
    return this;
  }
  
  /**
   * Sets the title of the constructed {@link Window}.
   * @param title a non-null string to be used as the title of the {@link Window}
   * @return this
   */
  public WindowBuilder withTitle(String title) {
    _title = Objects.requireNonNull(title);
    return this;
  }
  
  /**
   * Controls whether or not the constructed {@link Window} will be resizable.
   * @param resizable enable/disable resizing
   * @return this
   */
  public WindowBuilder withResizable(boolean resizable) {
    _resizable = resizable;
    return this;
  }
  
  /**
   * Sets the initial size of the constructed {@link Window}.
   * @param w the width of the {@link Window}
   * @param h the height of the {@link Window}
   * @return this
   */
  public WindowBuilder withSize(int w, int h) {
    _w = w; _h = h;
    return this;
  }
  
  /**
   * Sets the background colour of the constructed {@link Window}.
   * @param r the red component
   * @param g the green component
   * @param b the blue component
   * @return this
   */
  public WindowBuilder withBackgroundColour(float r, float g, float b) {
    _clear[0] = r;
    _clear[1] = g;
    _clear[2] = b;
    return this;
  }
  
  /**
   * Builds the {@link Window}.
   * @return a {@link Window} based on the input parameters
   * @throws LWJGLException
   */
  public Window build() throws LWJGLException {
    // Use default ContextBuilder if none was provided
    if(_builder == null) {
      _builder = new ContextBuilder();
    }
    
    // This class will be used to instantiate OpenGL in
    // a different thread, but still return a reference
    // to the instance from this method
    class State {
      Window window;
      LWJGLException e;
    }
    
    State state = new State();
    
    // Create OpenGL in a new thread
    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          // Try to create the display
          initDisplay();
          createDisplay(_builder);
        } catch(LWJGLException e) {
          // If it fails, dump the exception into the state object
          // so it can be rethrown by the enclosing function and bail
          state.e = e;
          state.notifyAll();
          return;
        }
        
        // Build the context itself
        Context<?> ctx = _builder.build();
        
        // Log some info about the environment
        logContextInfo();
        
        // Create the window and wake up the other thread
        synchronized(state) {
          state.window = createWindowFromContext(ctx);
          state.notifyAll();
        }
        
        // Run the context in this thread
        ctx.run();
      }
    }, "OpenGL"); //$NON-NLS-1$
    
    // Start the GL thread and wait for
    // either Window creation or an error
    synchronized(state) {
      thread.start();
      
      while(state.window == null && state.e == null) {
        try{
          state.wait();
        } catch(@SuppressWarnings("unused") InterruptedException e) { }
      }
    }
    
    // Rethrow the exception if we
    // caught one in the GL thread
    if(state.e != null) {
      throw state.e;
    }
    
    return state.window;
  }
  
  private void initDisplay() throws LWJGLException {
    Display.setTitle(_title);
    Display.setResizable(_resizable);
    Display.setInitialBackground(_clear[0], _clear[1], _clear[2]);
    Display.setDisplayMode(new DisplayMode(_w, _h));
  }
  
  private void createDisplay(ContextBuilder builder) throws LWJGLException {
    Display.create(builder.format(), builder.attribs());
  }
  
  private Window createWindowFromContext(Context<?> ctx) {
    return new Window(ctx);
  }
  
  private void logContextInfo() {
    logger.info("Creating context {}", Display.getTitle()); //$NON-NLS-1$
    logger.info("Display adapter: {}", Display.getAdapter()); //$NON-NLS-1$
    logger.info("Driver version:  {}", Display.getVersion()); //$NON-NLS-1$
    logger.info("OpenGL version:  {}", GL11.glGetString(GL11.GL_VERSION)); //$NON-NLS-1$
  }
}