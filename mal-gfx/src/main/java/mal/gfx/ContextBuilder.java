package mal.gfx;

public class ContextBuilder {
  private Window _window;
  
  public ContextBuilder window(Window window) {
    _window = window;
    return this;
  }
  
  public Context build() {
    return new Context(_window);
  }
}