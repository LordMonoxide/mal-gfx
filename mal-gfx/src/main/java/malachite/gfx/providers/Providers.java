package malachite.gfx.providers;

public class Providers {
  public final DrawableProvider drawable = new DrawableProvider();
  public final ShaderProvider   shader   = new ShaderProvider();
  
  public void refresh() {
    drawable.refresh();
    shader.refresh();
  }
}