package malachite.gfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowBuilder {
  public static void main(String[] args) {
    Window window = new WindowBuilder()
      .registerContext(malachite.gfx.gl32.ContextBuilder.class)
      .registerContext(malachite.gfx.gl21.ContextBuilder.class)
      .setTitle("Test").build();
  }
  
  private static final Logger logger = LoggerFactory.getLogger(WindowBuilder.class);
  
  private final List<Class<? extends AbstractContextBuilder>> _contexts = new ArrayList<>();
  
  private String _title;
  private boolean _resizable = true;
  private boolean _blending = true;
  private int _w = 1280, _h = 720;
  private final float[] _clear = new float[] {0, 0, 0};
  private int _fps = 60;
  
  public WindowBuilder registerContext(Class<? extends AbstractContextBuilder> context) {
    _contexts.add(Objects.requireNonNull(context));
    return this;
  }
  
  public WindowBuilder setTitle(String title) {
    _title = title;
    return this;
  }
  
  public WindowBuilder setResizable(boolean resizable) {
    _resizable = resizable;
    return this;
  }
  
  public WindowBuilder withAlphaBlending(boolean blending) {
    _blending = blending;
    return this;
  }
  
  public WindowBuilder setSize(int w, int h) {
    _w = w; _h = h;
    return this;
  }
  
  public WindowBuilder setClearColour(float r, float g, float b) {
    _clear[0] = r;
    _clear[1] = g;
    _clear[2] = b;
    return this;
  }
  
  public WindowBuilder setFPSLimit(int fps) {
    _fps = fps;
    return this;
  }
  
  public Window build() {
    if(_contexts.isEmpty()) {
      throw new NullPointerException("No context builders were registered before attempting to build a context"); //$NON-NLS-1$
    }
    
    AbstractContext ctx = buildSuitableContext(builder -> {
      initDisplay();
      createDisplay(builder);
      return builder.create(_blending, _clear, _w, _h, _fps);
    });
    
    logContextInfo();
    
    return createWindowFromContext(ctx);
  }
  
  private AbstractContext buildSuitableContext(ContextBuilderIteration r) {
    for(Class<? extends AbstractContextBuilder> c : _contexts) {
      try {
        try {
          AbstractContextBuilder ctx = c.newInstance();
          return r.build(ctx);
        } catch(LWJGLException e) {
          logger.error("Couldn't instanciate " + c, e);
        }
      } catch(InstantiationException | IllegalAccessException e) {
        logger.error("Error creating context", e); //$NON-NLS-1$
        e.printStackTrace();
      }
    }
    
    return null;
  }
  
  private void initDisplay() throws LWJGLException {
    Display.setTitle(_title);
    Display.setResizable(_resizable);
    Display.setInitialBackground(_clear[0], _clear[1], _clear[2]);
    Display.setDisplayMode(new DisplayMode(_w, _h));
  }
  
  private void createDisplay(AbstractContextBuilder builder) throws LWJGLException {
    Display.create(builder.createPixelFormat(), builder.createContextAttribs());
  }
  
  private Window createWindowFromContext(AbstractContext ctx) {
    return new Window(ctx);
  }
  
  private void logContextInfo() {
    logger.info("Creating context {}", Display.getTitle()); //$NON-NLS-1$
    logger.info("Display adapter: {}", Display.getAdapter()); //$NON-NLS-1$
    logger.info("Driver version:  {}", Display.getVersion()); //$NON-NLS-1$
    logger.info("OpenGL version:  {}", GL11.glGetString(GL11.GL_VERSION)); //$NON-NLS-1$
  }
  
  private interface ContextBuilderIteration {
    public AbstractContext build(AbstractContextBuilder ctx) throws LWJGLException;
  }
}