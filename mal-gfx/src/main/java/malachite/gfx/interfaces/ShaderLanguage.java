package malachite.gfx.interfaces;

import malachite.gfx.ShaderBuilderGLSL12;

public interface ShaderLanguage {
  public void version(ShaderBuilderGLSL12.StageBuilder builder);
  public void variable(ShaderBuilderGLSL12 builder, ShaderBuilderGLSL12.Variable variable);
  public void function(ShaderBuilderGLSL12.StageBuilder builder, ShaderBuilderGLSL12.StageBuilder.Function function);
  
  public void finalizeVSH(ShaderBuilderGLSL12.StageBuilder builder);
  public void finalizeFSH(ShaderBuilderGLSL12.StageBuilder builder);
}