package malachite.gfx.gl21;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

import malachite.gfx.AbstractContextBuilder;
import malachite.gfx.Context;

public class ContextBuilder extends AbstractContextBuilder {
  @Override protected void createDisplay() throws LWJGLException {
    PixelFormat format = new PixelFormat();
    ContextAttribs attribs = new ContextAttribs(2, 1);
    Display.create(format, attribs);
  }
  
  @Override protected Context createContext(int fps) {
    return null;
  }
}