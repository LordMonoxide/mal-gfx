package mal.gfx;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class StandardTexture implements Texture {
  private final int _id;
  private final int _w, _h;
  
  StandardTexture(int w, int h, ByteBuffer data) {
    _w = w;
    _h = h;
    
    _id = GL11.glGenTextures();
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, _id);
    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, _w, _h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
  }
  
  public int getID() { return _id; }
  public int getW()  { return _w; }
  public int getH()  { return _h; }
  
  @Override public void use() {
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, _id);
  }
  
  @Override public void destroy() {
    GL11.glDeleteTextures(_id);
  }
}