package malachite.gfx.interfaces;

import malachite.gfx.ShaderBuilder;

public interface ShaderLanguage {
  public void version(ShaderBuilder.StageBuilder builder);
  public void variable(ShaderBuilder builder, ShaderBuilder.Variable variable);
  public void function(ShaderBuilder.StageBuilder builder, ShaderBuilder.StageBuilder.Function function);
  
  public void finalizeVSH(ShaderBuilder.StageBuilder builder);
  public void finalizeFSH(ShaderBuilder.StageBuilder builder);
}