package malachite.gfx.gl21;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.PixelFormat;

import malachite.gfx.AbstractContextBuilder;
import malachite.gfx.AbstractContext;

public class ContextBuilder extends AbstractContextBuilder {
  @Override protected ContextAttribs createContextAttribs() throws LWJGLException {
    return new ContextAttribs(2, 1);
  }
  
  @Override protected PixelFormat createPixelFormat() {
    return new PixelFormat();
  }
  
  @Override protected AbstractContext createContext(float[] clearColour, int w, int h, int fps) {
    return null;
  }
}