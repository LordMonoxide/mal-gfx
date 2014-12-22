package mal.gfx.textures;

import mal.gfx.Context;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class GL30RenderTarget extends RenderTarget {
  private float _w, _h;
  
  public GL30RenderTarget(Context context, int w, int h) {
    super(context, w, h);
    
    use();
    GL30.glFramebufferTexture2D(renderID, GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, id, 0);
  }
  
  @Override protected int generateRenderID() {
    return GL30.glGenFramebuffers();
  }
  
  @Override protected void bind() {
    GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
    
    _w = _context.getW();
    _h = _context.getH();
    
    _context.setViewport(0, 0, (int)size.x, (int)size.y);
    
    _context.camera.proj.push();
    _context.camera.view.push();
    _context.matrices.push();
    
    //_context.setProjection(size.x, size.y);
    _context.camera.reset();
    _context.matrices.reset();
  }
  
  @Override protected void unbind() {
    GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    
    _context.matrices.pop();
    _context.camera.view.pop();
    _context.camera.proj.pop();
    
    _context.setViewport(0, 0, (int)_w, (int)_h);
  }
  
  @Override public void clear() {
    _context.clear();
  }
}