package mal.gfx.textures;

import org.lwjgl.opengl.EXTFramebufferObject;

import mal.gfx.Context;

public class EXTRenderTarget extends RenderTarget {
  public EXTRenderTarget(Context context, int w, int h) {
    super(context, w, h);
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