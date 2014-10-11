package malachite.gfx;

public class Window {
  private final AbstractContext _ctx;
  
  Window(AbstractContext ctx) {
    _ctx = ctx;
    _ctx.run();
  }
  
  public void close() {
    _ctx.destroy();
  }
}