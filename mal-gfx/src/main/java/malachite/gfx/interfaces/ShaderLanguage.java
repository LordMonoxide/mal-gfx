package malachite.gfx.interfaces;

import malachite.gfx.ShaderBuilder;

public interface ShaderLanguage {
  public String version();
  public String variable(ShaderBuilder.VARIABLE_MODE direction, String type, String name);
  public String function(ShaderBuilder.Function builder);
}