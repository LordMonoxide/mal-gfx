package malachite.gfx.providers;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import malachite.gfx.DrawableVBO;
import malachite.gfx.interfaces.Drawable;

public class DrawableProvider {
  private static Logger logger = LoggerFactory.getLogger(DrawableProvider.class);
  
  public Class<? extends Drawable> _drawable;
  
  public void refresh() {
    if(DrawableVBO.test()) {
      _drawable = DrawableVBO.class;
    }
  }
  
  public Drawable create(float[] vertices, byte[] indices) {
    try {
      return (Drawable)_drawable.getConstructors()[0].newInstance(vertices, indices);
    } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
      logger.error("A fatal error occurred when creating a new drawable.", e); //$NON-NLS-1$
      throw new RuntimeException(e);
    }
  }
}