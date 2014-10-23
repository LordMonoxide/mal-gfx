package malachite.gfx;

import malachite.gfx.interfaces.Drawable;
import malachite.gfx.providers.Providers;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Context<ProvidersT extends Providers> {
  public final ProvidersT providers;
  
  private boolean _running;
  
  private int    _fpsLimit;
  private double _lastSPF;
  private double _spfAvg;
  private int    _spfs;
  
  private final double[] _spf = new double[10];
  
  Context(int fps, ProvidersT providers) {
    this.providers = providers;
    _fpsLimit = fps;
    
    providers.refresh();
  }
  
  public void destroy() {
    if(_running) {
      _running = false;
    } else {
      cleanup();
      
      Display.destroy();
    }
  }
  
  private void cleanup() {
    
  }
  
  private void updateSize() {
    GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
  }
  
  void run() {
    updateSize();
    
    Drawable drawable = providers.drawable.create(
      new float[] {
        -0.5f,  0.5f, 0f,
        -0.5f, -0.5f, 0f,
         0.5f, -0.5f, 0f,
         0.5f,  0.5f, 0f,
      }, new byte[] {
        0, 1, 2,
        2, 3, 0
      }
    );
    
    _running = true;
    
    while(_running) {
      checkContext();
      clearContext();
      drawScene();
      drawable.draw();
      updateContext();
      updateFrameRate();
      syncContext();
    }
    
    drawable.destroy();
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