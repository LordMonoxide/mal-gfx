package malachite.gfx;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public abstract class AbstractContextBuilder {
  protected abstract ContextAttribs  createContextAttribs() throws LWJGLException;
  protected abstract PixelFormat     createPixelFormat();
  protected abstract AbstractContext createContext(int fps);
  
  protected AbstractContextBuilder() { }
  
  final AbstractContext create(boolean blending, float[] clearColour, int w, int h, int fps) throws LWJGLException {
    if(blending) {
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    return createContext(fps);
  }
}