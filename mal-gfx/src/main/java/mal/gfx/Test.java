package mal.gfx;

import mal.gfx.shaders.Shader;
import mal.gfx.textures.Texture;
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
      
      Texture t = _context.textures.getTexture("mal.png");
      
      _shader = _context.shaders.create().build();
      
      _drawable = new TexturedDrawableVBO(
        new float[] {
          -256f, -256f, 0, 0, 0,
          -256f,  256f, 0, 0, 1,
           256f,  256f, 0, 1, 1,
           256f, -256f, 0, 1, 0
        }, new byte[] {
          0, 1, 2,
          2, 3, 0
        }, _context.matrices, t, _shader
      );
      
      _drawable.pos.translate(-200, 0, 0);
    }).onClose(() -> {
      _window.destroy();
    }).onLoop(() -> {
      _context.clear();
      _drawable.draw();
    });
  }
}