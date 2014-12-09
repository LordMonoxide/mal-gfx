package mal.gfx.vbo;

import org.lwjgl.opengl.GL30;

public class VertexArray {
  private final int _id;
  
  VertexArray() {
    _id = GL30.glGenVertexArrays();
  }
  
  public void destroy() {
    GL30.glDeleteVertexArrays(_id);
  }
  
  public void bind(Runnable r) {
    GL30.glBindVertexArray(_id);
    r.run();
    GL30.glBindVertexArray(0);
  }
}