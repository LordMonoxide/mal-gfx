package mal.gfx;

import mal.gfx.shaders.ShaderManager;
import mal.gfx.textures.TextureLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Context {
  private final GLContext _gl;
  
  public final Camera camera = new Camera();
  
  public final TextureLoader textures = new TextureLoader();
  public final ShaderManager shaders = new ShaderManager(this);
  public final MatrixStack   matrices = new MatrixStack();
  
  private int _clear_mode = GL11.GL_COLOR_BUFFER_BIT;
  
  private float _w, _h;
  
  public Context(GLContext gl, boolean alpha, boolean depth, int w, int h) {
    _gl = gl;
    
    if(alpha) {
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    if(depth) {
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      _clear_mode |= GL11.GL_DEPTH_BUFFER_BIT;
    }
    
    GL11.glClearColor(1, 1, 1, 1);
    
    setViewport(0, 0, w, h);
    setProjection(w, h);
    
    camera.reset();
  }
  
  public float getW() { return _w; }
  public float getH() { return _h; }
  
  public void setViewport(int x, int y, int w, int h) {
    GL11.glViewport(x, y, w, h);
  }
  
  public void setProjection(float w, float h) {
    _w = w; _h = h;
    camera.proj.set(Matrices.symmetricPerspective(w / 2f / 100f, h / 2f / 100f, 0, 1000));
  }
  
  public void clear() {
    GL11.glClear(_clear_mode);
  }
}