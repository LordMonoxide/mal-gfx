package mal.gfx;

import mal.gfx.shaders.Shader;
import mal.gfx.shaders.ShaderBuilder;
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
      
      ShaderBuilder s = new ShaderBuilder();
      _shader = s.build();
    }).onClose(() -> {
      _window.destroy();
    }).onLoop(() -> {
      _shader.use();
      _drawable.draw();
    });
  }
}