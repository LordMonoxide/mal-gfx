package malachite.gfx.gl32;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.PixelFormat;

import malachite.gfx.AbstractContext;
import malachite.gfx.AbstractContextBuilder;

public class ContextBuilder extends AbstractContextBuilder {
  @Override protected ContextAttribs createContextAttribs() throws LWJGLException {
    return new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
  }
  
  @Override protected PixelFormat createPixelFormat() {
    return new PixelFormat();
  }
  
  @Override protected AbstractContext createContext(int fps) {
    return new Context(fps);
  }
}