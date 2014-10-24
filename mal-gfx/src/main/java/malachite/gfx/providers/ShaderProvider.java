package malachite.gfx.providers;

import malachite.gfx.ShaderBuilderGLSL15;
import malachite.gfx.interfaces.ShaderBuilder;

public class ShaderProvider {
  private Class<? extends ShaderBuilder> _shader;
  
  public void refresh() {
    if(ShaderBuilderGLSL15.test()) {
      _shader = ShaderBuilderGLSL15.class;
    }
  }
}