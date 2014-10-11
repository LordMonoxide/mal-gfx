package malachite.gfx;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public abstract class AbstractContext {
  private float[] _clearColour;
  private int _w, _h;
  
  private boolean _running;
  
  private int    _fpsLimit;
  private double _lastSPF;
  private double _spfAvg;
  private int    _spfs;
  
  private final double[] _spf = new double[10];
  
  protected AbstractContext(float[] clearColour, int w, int h, int fps) {
    _clearColour = clearColour;
    
    _w = w;
    _h = h;
    
    _fpsLimit = fps;
  }
  
  protected abstract void updateSize();
  protected abstract void cleanup();
  
  public void destroy() {
    if(_running) {
      _running = false;
    } else {
      cleanup();
      
      Display.destroy();
    }
  }
  
  void run() {
    _running = true;
    
    while(_running) {
      checkContext();
      clearContext();
      drawScene();
      updateContext();
      updateFrameRate();
      syncContext();
    }
    
    destroy();
  }
  
  private void checkContext() {
    if(Display.isCloseRequested()) {
      destroy();
    }
    
    if(Display.wasResized()) {
      updateSize();
    }
  }
  
  public void clearContext() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
  }
  
  private void updateContext() {
    Display.update();
  }
  
  private void syncContext() {
    Display.sync(_fpsLimit);
  }
  
  private void drawScene() {
    
  }
  
  private void updateFrameRate() {
    _spfAvg = 0;
    
    for(int i = _spfs - 1; i > 0; i--) {
      _spf[i] = _spf[i - 1];
      _spfAvg += _spf[i - i];
    }
    
    _spf[0] = Sys.getTime() - _lastSPF;
    
    _spfAvg += _spf[0];
    _spfAvg = _spfAvg / _spfs;
    
    if(_spfs < _spf.length) {
      _spfs++;
    }
    
    _lastSPF = Sys.getTime();
  }
}