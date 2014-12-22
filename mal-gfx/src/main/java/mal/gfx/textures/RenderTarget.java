package mal.gfx.textures;

import mal.gfx.Context;

public abstract class RenderTarget extends StandardTexture {
  public final int renderID;
  
  protected final Context _context;
  
  public RenderTarget(Context context, int w, int h) {
    super(w, h, null);
    
    renderID = generateID();
    
    _context = context;
  }
  
  protected abstract int generateRenderID();
  protected abstract void bind();
  protected abstract void unbind();
  
  public abstract void clear();
  
  public void bind(Runnable r) {
    bind();
    r.run();
    unbind();
  }
}