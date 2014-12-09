package mal.gfx.vbo;

import org.lwjgl.opengl.GL15;

public class VertexBuffer extends Buffer {
  VertexBuffer() {
    super(GL15.GL_ARRAY_BUFFER);
  }
}