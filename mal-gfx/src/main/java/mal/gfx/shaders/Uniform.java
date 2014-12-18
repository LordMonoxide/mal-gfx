package mal.gfx.shaders;

import java.nio.FloatBuffer;

import mal.gfx.Buffers;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

public class Uniform {
  private final int _id;
  
  Uniform(int id) {
    _id = id;
  }
  
  public void set(Matrix4f matrix) {
    GL20.glUniformMatrix4(_id, false, Buffers.of(matrix));
  }
  
  public void set(FloatBuffer buffer) {
    GL20.glUniformMatrix4(_id, false, buffer);
  }
}