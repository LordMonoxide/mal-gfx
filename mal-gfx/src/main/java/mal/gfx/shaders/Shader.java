package mal.gfx.shaders;

import org.lwjgl.opengl.GL20;

public class Shader {
  private static int _current = -1;
  
  private final int _id;
  
  public Shader(int id) {
    _id = id;
  }
  
  public void use() {
    if(_current != _id) {
      _current = _id;
      GL20.glUseProgram(_id);
    }
  }
}