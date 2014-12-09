package mal.gfx;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

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
}