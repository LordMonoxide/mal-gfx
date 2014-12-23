package mal.gfx.textures;

import mal.gfx.Context;

public abstract class RenderTarget extends StandardTexture {
  public final int renderID;
  
  protected final Context _context;
  
  private int _w, _h;
  
  public RenderTarget(Context context, int w, int h) {
    super(w, h, null);
    
    renderID = generateID();
    
    _context = context;
  }
  
  protected abstract int generateRenderID();
  protected abstract void bindImpl();
  protected abstract void unbindImpl();
  
  public void clear() {
    _context.clear();
  }
  
  public final void bind() {
    bindImpl();
    
    _w = _context.getW();
    _h = _context.getH();
    
    _context.setViewport(0, 0, (int)size.x, (int)size.y);
    
    _context.camera.proj.push();
    _context.camera.view.push();
    _context.matrices.push();
    
    _context.setProjection(size.x, size.y);
    _context.camera.reset();
    _context.matrices.reset();
  }
  
  public final void unbind() {
    unbindImpl();
    
    _context.matrices.pop();
    _context.camera.view.pop();
    _context.camera.proj.pop();
    
    _context.setViewport(0, 0, _w, _h);
  }
  
  public final void bind(Runnable r) {
    bind();
    r.run();
    unbind();
  }
}