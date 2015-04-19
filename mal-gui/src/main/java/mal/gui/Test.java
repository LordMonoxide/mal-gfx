package mal.gui;

import mal.gfx.Context;
import mal.gfx.ContextBuilder;
import mal.gfx.TexturedDrawable;
import mal.gfx.Window;
import mal.gfx.WindowBuilder;
import mal.gfx.shaders.Shader;
import mal.gfx.textures.GL30RenderTarget;
import mal.gfx.textures.RenderTarget;
import mal.gfx.textures.Texture;
import mal.gfx.vbo.TexturedDrawableVBO;

public class Test {
  public static void main(String[] args) {
    new Test();
  }
  
  private Window _window;
  private Context _context;
  private TexturedDrawable _drawable, _drawable2;
  private Shader _shader;
  private RenderTarget _t;
  
  public Test() {
    _window = new WindowBuilder().title("Malachite").build();
    
    _window.events.onCreate(() -> {
      _context = new ContextBuilder()
        .forWindow(_window)
        .withAlpha()
        .withDepth()
        .build();
      
      Texture t = _context.textures.getTexture("cat.png");
      
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
      
      _t = new GL30RenderTarget(_context, (int)t.size.x, (int)t.size.y);

      _drawable2 = new TexturedDrawableVBO(
        new float[] {
          -256f, -256f, 0, 0, 0,
          -256f,  256f, 0, 0, 1,
           256f,  256f, 0, 1, 1,
           256f, -256f, 0, 1, 0
        }, new byte[] {
          0, 1, 2,
          2, 3, 0
        }, _context.matrices, _t, _shader
      );
    }).onClose(() -> {
      _window.destroy();
    }).onLoop(() -> {
      _t.bind(() -> {
        _t.clear();
        _drawable.draw();
      });
      
      _context.clear();
      _drawable2.draw();
    });
  }
}