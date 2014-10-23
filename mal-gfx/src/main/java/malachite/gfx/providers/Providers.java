package malachite.gfx.providers;

public class Providers {
  public final DrawableProvider drawable = new DrawableProvider();
  
  public void refresh() {
    drawable.refresh();
  }
}