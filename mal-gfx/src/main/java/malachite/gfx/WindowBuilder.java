package malachite.gfx;

import java.util.Objects;

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
    try {
      Window window = new WindowBuilder()
        .withTitle("Test").build();
    } catch(LWJGLException e) {
      logger.error("Error while building window", e);
    }
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
    class State {
      Window window;
      LWJGLException e;
    }
    
    State state = new State();
    
    Thread thread = new Thread(new Runnable() {
      public void run() {
        if(_builder == null) {
          _builder = new ContextBuilder();
        }
        
        try {
          initDisplay();
          createDisplay(_builder);
        } catch(LWJGLException e) {
          state.e = e;
        }
        
        Context ctx = _builder.build(_w, _h);
        
        logContextInfo();
        
        synchronized(state) {
          state.window = createWindowFromContext(ctx);
          state.notifyAll();
        }
        
        ctx.run();
      }
    });
    
    synchronized(state) {
      thread.start();
      
      while(state.window == null) {
        try{
          state.wait();
        } catch(InterruptedException e) {
        }
      }
    }
    
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
  
  private Window createWindowFromContext(Context ctx) {
    return new Window(ctx);
  }
  
  private void logContextInfo() {
    logger.info("Creating context {}", Display.getTitle()); //$NON-NLS-1$
    logger.info("Display adapter: {}", Display.getAdapter()); //$NON-NLS-1$
    logger.info("Driver version:  {}", Display.getVersion()); //$NON-NLS-1$
    logger.info("OpenGL version:  {}", GL11.glGetString(GL11.GL_VERSION)); //$NON-NLS-1$
  }
}