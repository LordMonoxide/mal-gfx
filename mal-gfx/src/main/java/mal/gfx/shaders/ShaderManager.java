package mal.gfx.shaders;

import mal.gfx.Context;

public class ShaderManager {
  private final Context _context;
  
  private Shader _current;
  
  public ShaderManager(Context context) {
    _context = context;
  }
  
  public Shader getCurrent() {
    return _current;
  }
  
  boolean setCurrent(Shader shader) {
    shader.proj.set(_context.camera.projBuffer);
    shader.view.set(_context.camera.viewBuffer);
    
    if(_current != shader) {
      _current = shader;
      return true;
    }
    
    return false;
  }
  
  public ShaderBuilder create() {
    return new ShaderBuilder(this);
  }
}