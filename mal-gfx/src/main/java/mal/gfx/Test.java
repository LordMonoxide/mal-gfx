package mal.gfx;

import mal.gfx.shaders.Shader;
import mal.gfx.vbo.TexturedDrawableVBO;

public class Test {
  public static void main(String[] args) {
    new Test();
  }
  
  private Window _window;
  private Context _context;
  private TexturedDrawable _drawable;
  private Shader _shader;
  
  public Test() {
    _window = new WindowBuilder().title("Malachite").build();
    
    _window.events.onCreate(() -> {
      _context = new ContextBuilder()
        .forWindow(_window)
        .withAlpha()
        .withDepth()
        .build();
      
      Texture t = _context.textures.getTexture("box.png");
      
      _drawable = new TexturedDrawableVBO(
        new float[] {
           0,  0, -1, 0, 0,
           0, 32, -1, 0, 1,
          32, 32, -1, 1, 1,
          32,  0, -1, 1, 0
        }, new byte[] {
          0, 1, 2,
          2, 3, 0
        }, t
      );
      
      _shader = _context.shaders.create().build();
    }).onClose(() -> {
      _window.destroy();
    }).onLoop(() -> {
      _context.clear();
      _shader.proj.set(_context.matrices.getProjectionBuffer());
      _shader.view.set(_context.matrices.getViewBuffer());
      _shader.use();
      _drawable.draw();
    });
  }
}