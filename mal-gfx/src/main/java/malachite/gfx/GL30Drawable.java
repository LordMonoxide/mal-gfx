package malachite.gfx;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

/**
 * An implementation of {@link:Drawable} using VBOs.
 * Requires GL_ARB_vertex_array_object
 * @author Corey Frenette
 */
public class GL30Drawable {
  private int _vaID;
  private int _vbID;
  private int _ibID;
  private int _indices;
  
  GL30Drawable() {
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
    
    _indices = indices.length;
    ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(_indices);
    indicesBuffer.put(indices);
    indicesBuffer.flip();
    
    _vaID = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(_vaID);
    
    System.out.println(GLContext.getCapabilities().GL_ARB_vertex_array_object);
    
    int vbID = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbID);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    
    GL30.glBindVertexArray(0);
    
    _ibID = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, _ibID);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
  }
  
  public void destroy() {
    GL15.glDeleteBuffers(_vbID);
    GL15.glDeleteBuffers(_ibID);
    GL30.glDeleteVertexArrays(_vaID);
  }
  
  public void draw() {
    GL30.glBindVertexArray(_vaID);
    GL20.glEnableVertexAttribArray(0);
    
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, _ibID);
    
    GL11.glDrawElements(GL11.GL_TRIANGLES, _indices, GL11.GL_UNSIGNED_BYTE, 0);
    
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL20.glDisableVertexAttribArray(0);
    GL30.glBindVertexArray(0);
  }
}