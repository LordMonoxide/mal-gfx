package mal.gfx.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
  private final int _id;
  private final ShaderManager _manager;
  
  public final Uniform proj, view, model;
  
  public Shader(int id, ShaderManager manager) {
    _id = id;
    _manager = manager;
    
    proj  = getUniform("in_proj");
    view  = getUniform("in_view");
    model = getUniform("in_model");
  }
  
  public void use() {
    if(_manager.setCurrent(this)) {
      GL20.glUseProgram(_id);
    }
  }
  
  public Uniform getUniform(String name) {
    int id = GL20.glGetUniformLocation(_id, name);
    
    if(id == -1) {
      System.out.println(name + " wat");
    }
    
    return new Uniform(id);
  }
}