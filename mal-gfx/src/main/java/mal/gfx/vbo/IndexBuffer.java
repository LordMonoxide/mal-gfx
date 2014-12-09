package mal.gfx.vbo;

import org.lwjgl.opengl.GL15;

public class IndexBuffer extends Buffer {
  IndexBuffer() {
    super(GL15.GL_ELEMENT_ARRAY_BUFFER);
  }
}