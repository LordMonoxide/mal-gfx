package malachite.gfx.interfaces;

import java.util.ArrayList;
import java.util.List;

import malachite.gfx.Shader;
import malachite.gfx.shaders.Fragment;

public abstract class ShaderBuilder {
  protected List<Fragment> _fragments = new ArrayList<>();
  
  public ShaderBuilder recolour() {
    _fragments.add(newRecolour());
    return this;
  }
  
  public ShaderBuilder desaturate() {
    _fragments.add(newDesaturate());
    return this;
  }
  
  public abstract Shader build();
  
  protected abstract Fragment newRecolour();
  protected abstract Fragment newDesaturate();
}