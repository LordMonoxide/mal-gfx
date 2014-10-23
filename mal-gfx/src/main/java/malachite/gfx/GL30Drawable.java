package malachite.gfx;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import malachite.gfx.interfaces.Drawable;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

/**
 * <p>An implementation of {@link Drawable} using VBOs.</p>
 * <p><b>Requires</b> {@code OpenGL 3.0} <i>or</i> {@code OpenGL 2.0}
 * with {@code GL_ARB_vertex_array_object} extension</p>
 * @author Corey Frenette
 */
public class GL30Drawable implements Drawable {
  public static boolean test() {
    ContextCapabilities caps = GLContext.getCapabilities();
    
    return caps.OpenGL30
        || caps.OpenGL21 && caps.GL_ARB_vertex_buffer_object;
  }
  
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
    
    ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(_indices);
    indicesBuffer.put(indices);
    indicesBuffer.flip();
    
    updateVBO(verticesBuffer, indicesBuffer);
  }
  
  private void updateVBO(FloatBuffer vertices, ByteBuffer indices) {
    _indices = indices.remaining();
    
    _vaID = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(_vaID);
    
    int vbID = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbID);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    
    GL30.glBindVertexArray(0);
    
    _ibID = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, _ibID);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
  }
  
  @Override public void destroy() {
    GL15.glDeleteBuffers(_vbID);
    GL15.glDeleteBuffers(_ibID);
    GL30.glDeleteVertexArrays(_vaID);
  }
  
  @Override public void draw() {
    GL30.glBindVertexArray(_vaID);
    GL20.glEnableVertexAttribArray(0);
    
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, _ibID);
    
    GL11.glDrawElements(GL11.GL_TRIANGLES, _indices, GL11.GL_UNSIGNED_BYTE, 0);
    
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL20.glDisableVertexAttribArray(0);
    GL30.glBindVertexArray(0);
  }
}