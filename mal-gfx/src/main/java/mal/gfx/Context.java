package mal.gfx;

import mal.gfx.shaders.ShaderManager;
import mal.gfx.textures.TextureLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Context {
  private final GLContext _gl;
  
  public final TextureLoader textures = new TextureLoader();
  public final ShaderManager shaders = new ShaderManager();
  public final MatrixStack   matrices = new MatrixStack();
  
  private int _clear_mode = GL11.GL_COLOR_BUFFER_BIT;
  
  public Context(GLContext gl, boolean alpha, boolean depth, int w, int h) {
    _gl = gl;
    
    if(alpha) {
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    if(depth) {
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      _clear_mode |=  GL11.GL_DEPTH_BUFFER_BIT;
    }
    
    GL11.glViewport(0, 0, w, h);
    
    matrices.setProjection(MatrixStack.symmetricPerspective(w / 2f / 100f, h / 2f / 100f, 0, 1000));
    //matrices.setProjection(MatrixStack.perspectiveFOV((float)(Math.PI / 4), (float)w / (float)h, 0f, 1000.0f));
  }
  
  public void clear() {
    GL11.glClear(_clear_mode);
  }
}