package malachite.gfx;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Context {
  private boolean _running;
  
  private int    _fpsLimit;
  private double _lastSPF;
  private double _spfAvg;
  private int    _spfs;
  
  private final double[] _spf = new double[10];
  
  Context(int fps) {
    _fpsLimit = fps;
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
    
    float[] vertices = {
      -0.5f,  0.5f, 0f,
      -0.5f, -0.5f, 0f,
       0.5f, -0.5f, 0f,
       0.5f,  0.5f, 0f,
    };
    
    byte[] indices = {
      // Left bottom triangle
      0, 1, 2,
      // Right top triangle
      2, 3, 0
    };
    
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices);
    verticesBuffer.flip();
    
    int indicesCount = indices.length;
    ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
    indicesBuffer.put(indices);
    indicesBuffer.flip();
    
    int vaoId = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(vaoId);
    
    int vboId = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    
    GL30.glBindVertexArray(0);
    
    int vboiId = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    
    _running = true;
    
    while(_running) {
      checkContext();
      clearContext();
      drawScene();
      
      
      GL30.glBindVertexArray(vaoId);
      GL20.glEnableVertexAttribArray(0);
      
      GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
      
      GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
      
      GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
      GL20.glDisableVertexAttribArray(0);
      GL30.glBindVertexArray(0);
      
      
      updateContext();
      updateFrameRate();
      syncContext();
    }
    
    GL20.glDisableVertexAttribArray(0);
    
    // Delete the VBO
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    GL15.glDeleteBuffers(vboId);
    
    // Delete the VAO
    GL30.glBindVertexArray(0);
    GL30.glDeleteVertexArrays(vaoId);
    
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