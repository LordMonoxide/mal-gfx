package malachite.gfx.gl21;

import org.lwjgl.LWJGLException;

import malachite.gfx.AbstractContextBuilder;
import malachite.gfx.Context;

public class ContextBuilder extends AbstractContextBuilder {
  @Override protected void createDisplay() throws LWJGLException {
    
  }
  
  @Override protected Context createContext(int fps) {
    return null;
  }
}