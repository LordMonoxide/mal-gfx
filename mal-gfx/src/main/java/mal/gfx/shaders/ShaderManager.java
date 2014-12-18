package mal.gfx.shaders;

public class ShaderManager {
  private Shader _current;
  
  public Shader getCurrent() {
    return _current;
  }
  
  boolean setCurrent(Shader shader) {
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