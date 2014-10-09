package malachite.gfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.lwjgl.LWJGLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowBuilder {
  public static void main(String[] args) {
    Context ctx = new WindowBuilder()
      .registerContext(context)
      .setTitle("Test").build();
  }
  
  private static final Logger logger = LoggerFactory.getLogger(WindowBuilder.class);
  
  private final List<Class<? extends ContextBuilder>> _contexts = new ArrayList<>();
  
  private String _title;
  private boolean _resizable = true;
  private boolean _blending = true;
  private int _w = 1280, _h = 720;
  private final float[] _clear = new float[] {0, 0, 0};
  private int _fps = 60;
  
  public WindowBuilder registerContext(Class<? extends ContextBuilder> context) {
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
  
  public Context build() {
    if(_contexts.isEmpty()) {
      throw new NullPointerException("No context builders were registered before attempting to build a context"); //$NON-NLS-1$
    }
    
    for(Class<? extends ContextBuilder> c : _contexts) {
      try {
        ContextBuilder context = c.newInstance();
        
        try {
          return context.create(_title, _resizable, _blending, _clear, _w, _h, _fps);
        } catch(LWJGLException e) {
          logger.error("Couldn't instanciate " + context, e);
        }
      } catch(InstantiationException | IllegalAccessException e) {
        logger.error("Error creating context", e); //$NON-NLS-1$
        e.printStackTrace();
      }
    }
    
    return null;
  }
}