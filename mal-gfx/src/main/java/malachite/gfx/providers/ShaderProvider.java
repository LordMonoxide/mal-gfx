package malachite.gfx.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import malachite.gfx.ShaderLanguageGLSL15;
import malachite.gfx.interfaces.ShaderLanguage;

public class ShaderProvider {
  private static Logger logger = LoggerFactory.getLogger(DrawableProvider.class);
  
  private Class<? extends ShaderLanguage> _shader;
  
  public void refresh() {
    if(ShaderLanguageGLSL15.test()) {
      _shader = ShaderLanguageGLSL15.class;
    }
  }
  
  public ShaderLanguage create() {
    try {
      return _shader.newInstance();
    } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
      logger.error("A fatal error occurred when creating a new drawable.", e); //$NON-NLS-1$
      throw new RuntimeException(e);
    }
  }
}