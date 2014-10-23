package malachite.gfx.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import malachite.gfx.GL30Drawable;
import malachite.gfx.interfaces.Drawable;

public class DrawableProvider {
  private static Logger logger = LoggerFactory.getLogger(DrawableProvider.class);
  
  public Class<? extends Drawable> _drawable;
  
  public DrawableProvider() {
    if(GL30Drawable.test()) {
      _drawable = GL30Drawable.class;
    }
  }
  
  public Drawable create() {
    try {
      return _drawable.newInstance();
    } catch(InstantiationException | IllegalAccessException e) {
      logger.error("A fatal error occurred when creating a new drawable.", e);
      throw new RuntimeException(e);
    }
  }
}