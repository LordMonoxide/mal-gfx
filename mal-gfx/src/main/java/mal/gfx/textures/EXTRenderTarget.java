package mal.gfx.textures;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import mal.gfx.Context;

public class EXTRenderTarget extends RenderTarget {
  public EXTRenderTarget(Context context, int w, int h) {
    super(context, w, h);
    
    use();
    bind();
    EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, id, 0);
    unbind();
  }
  
  @Override protected int generateRenderID() {
    return EXTFramebufferObject.glGenFramebuffersEXT();
  }
  
  @Override protected void bindImpl() {
    EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, renderID);
  }
  
  @Override protected void unbindImpl() {
    EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
  }
}