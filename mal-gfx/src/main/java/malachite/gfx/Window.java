package malachite.gfx;

public class Window {
  private final AbstractContext _ctx;
  
  Window(AbstractContext ctx) {
    _ctx = ctx;
  }
  
  public void close() {
    _ctx.destroy();
  }
}