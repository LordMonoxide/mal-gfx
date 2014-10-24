package malachite.gfx.providers;

import malachite.gfx.ShaderLanguageGLSL15;
import malachite.gfx.interfaces.ShaderLanguage;

public class ShaderProvider {
  private Class<? extends ShaderLanguage> _shader;
  
  public void refresh() {
    if(ShaderLanguageGLSL15.test()) {
      _shader = ShaderLanguageGLSL15.class;
    }
  }
}