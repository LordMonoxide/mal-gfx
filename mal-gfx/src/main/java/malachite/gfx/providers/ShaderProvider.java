package malachite.gfx.providers;

import malachite.gfx.ShaderGLSL15;
import malachite.gfx.interfaces.Shader;

public class ShaderProvider {
  private Class<? extends Shader> _shader;
  
  public void refresh() {
    if(ShaderGLSL15.test()) {
      _shader = ShaderGLSL15.class;
    }
  }
}