package mal.gfx.vbo;

import org.lwjgl.opengl.GL15;

public abstract class Buffer {
  private final int _type;
  private final int _id;
  
  Buffer(int type) {
    _type = type;
    
    if(_type != 0) {
      _id = GL15.glGenBuffers();
    } else {
      _id = 0;
    }
  }
  
  public void destroy() {
    GL15.glDeleteBuffers(_id);
  }
  
  public void bind(Runnable r) {
    GL15.glBindBuffer(_type, _id);
    r.run();
    GL15.glBindBuffer(_type, 0);
  }
}