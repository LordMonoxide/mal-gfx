package mal.gfx.textures;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class StandardTexture extends Texture {
  StandardTexture(int w, int h, ByteBuffer data) {
    super(w, h);
    
    use();
    
    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
  }
  
  @Override protected int generateID() {
    return GL11.glGenTextures();
  }
  
  @Override public void use() {
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
  }
  
  @Override public void destroy() {
    GL11.glDeleteTextures(id);
  }
}