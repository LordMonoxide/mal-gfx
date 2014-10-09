package malachite.gfx;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class ContextBuilder {
  private static final Logger logger = LoggerFactory.getLogger(ContextBuilder.class);
  
  protected abstract void    createDisplay() throws LWJGLException;
  protected abstract Context createContext(int fps);
  
  final Context create(String title, boolean resizable, boolean blending, float[] clearColour, int w, int h, int fps) throws LWJGLException {
    Display.setTitle(title);
    Display.setResizable(resizable);
    Display.setInitialBackground(clearColour[0], clearColour[1], clearColour[2]);
    Display.setDisplayMode(new DisplayMode(w, h));
    createDisplay();
    
    logger.info("Creating context {}", Display.getTitle()); //$NON-NLS-1$
    logger.info("Display adapter: {}", Display.getAdapter()); //$NON-NLS-1$
    logger.info("Driver version:  {}", Display.getVersion()); //$NON-NLS-1$
    logger.info("OpenGL version:  {}", GL11.glGetString(GL11.GL_VERSION)); //$NON-NLS-1$
    
    if(blending) {
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    return createContext(fps);
  }
}