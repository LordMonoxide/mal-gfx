package mal.gfx;

public class ContextBuilder {
  private Window _window;
  
  public ContextBuilder(Window window) {
    _window = window;
  }
  
  public Context build() {
    return new Context(_window);
  }
}