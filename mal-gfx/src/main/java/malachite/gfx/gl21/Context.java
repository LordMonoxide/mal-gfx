package malachite.gfx.gl21;

import malachite.gfx.AbstractContext;

public class Context extends AbstractContext {
  Context(float[] clearColour, int w, int h, int fps) {
    super(clearColour, w, h, fps);
  }
  
  @Override protected void updateSize() {
    
  }
  
  @Override protected void cleanup() {
    
  }
}