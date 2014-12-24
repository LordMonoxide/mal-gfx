package mal.gfx.textures;

import mal.gfx.Context;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GL30RenderTarget extends RenderTarget {
  public GL30RenderTarget(Context context, int w, int h) {
    super(context, w, h);
    
    use();
    GL30.glFramebufferTexture2D(renderID, GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, id, 0);
    GL20.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
    
    if(GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
      System.err.println("Framebuffer incomplete!\n" + GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER));
    }
  }
  
  @Override protected int generateRenderID() {
    return GL30.glGenFramebuffers();
  }
  
  @Override protected void bindImpl() {
    GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, renderID);
  }
  
  @Override protected void unbindImpl() {
    GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
  }
}