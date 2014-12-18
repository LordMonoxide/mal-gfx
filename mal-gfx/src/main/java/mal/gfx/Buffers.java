package mal.gfx;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class Buffers {
  private Buffers() { }
  
  public static FloatBuffer of(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
  
  public static ByteBuffer of(byte[] data) {
    ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
  
  public static FloatBuffer of(Matrix4f data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    data.store(buffer);
    buffer.flip();
    return buffer;
  }
}