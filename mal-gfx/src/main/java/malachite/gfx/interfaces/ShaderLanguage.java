package malachite.gfx.interfaces;

import malachite.gfx.ShaderBuilder;

public interface ShaderLanguage {
  public void version(ShaderBuilder builder);
  public void variable(ShaderBuilder builder, ShaderBuilder.Variable variable);
  public void function(ShaderBuilder builder, ShaderBuilder.Function function);
}