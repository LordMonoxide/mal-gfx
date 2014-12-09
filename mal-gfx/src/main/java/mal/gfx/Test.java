package mal.gfx;

import mal.gfx.vbo.TexturedDrawableVBO;

public class Test {
  private Window _window;
  private Context _context;
  private TexturedDrawable _drawable;
  
  public Test() {
    _window = new WindowBuilder().title("Malachite").build();
    
    _window.events.onCreate(() -> {
      _context = new ContextBuilder(_window).build();
      
      Texture t = _context.textures.getTexture("mal.png");
      
      _drawable = new TexturedDrawableVBO(
        new float[] {
          -0.5f,  0.5f, 0f,
          -0.5f, -0.5f, 0f,
           0.5f, -0.5f, 0f,
           0.5f,  0.5f, 0f,
        }, new byte[] {
          0, 1, 2,
          2, 3, 0
        }, t
      );
    }).onClose(() -> {
      _window.destroy();
    }).onLoop(() -> {
      _drawable.draw();
    });
  }
}