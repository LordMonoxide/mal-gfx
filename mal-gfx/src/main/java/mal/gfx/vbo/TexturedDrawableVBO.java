package mal.gfx.vbo;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import mal.gfx.Buffers;
import mal.gfx.MatrixStack;
import mal.gfx.TexturedDrawable;
import mal.gfx.shaders.Shader;
import mal.gfx.textures.Texture;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

/**
 * <p>An implementation of {@link TexturedDrawable} using VBOs.</p>
 * <p><b>Requires</b> {@code OpenGL 3.0} <i>or</i> {@code OpenGL 2.0}
 * with {@code GL_ARB_vertex_array_object} extension</p>
 * @author Corey Frenette
 */
public class TexturedDrawableVBO extends TexturedDrawable {
  public static boolean test(GLContext gl) {
    ContextCapabilities caps = gl.getCapabilities();
    
    return caps.OpenGL30
        || caps.OpenGL21 && caps.GL_ARB_vertex_buffer_object;
  }
  
  //private VertexArray _va;
  private Buffer _vb;
  private Buffer _ib;
  private int _vertices;
  private int _indices;
  
  public TexturedDrawableVBO(float[] vertices, MatrixStack matrices, Texture texture, Shader shader) {
    super(vertices, matrices, texture, shader);
    
    //_va = VBO.createVertexArray();
    _vb = VBO.createVertexBuffer();
    _ib = VBO.createNoOpBuffer();
    
    FloatBuffer verticesBuffer = Buffers.of(vertices);
    updateVBO(verticesBuffer, null);
  }
  
  public TexturedDrawableVBO(float[] vertices, byte[] indices, MatrixStack matrices, Texture texture, Shader shader) {
    super(vertices, indices, matrices, texture, shader);
    
    //_va = VBO.createVertexArray();
    _vb = VBO.createVertexBuffer();
    _ib = VBO.createIndexBuffer();
    
    FloatBuffer verticesBuffer = Buffers.of(vertices);
    ByteBuffer indicesBuffer = Buffers.of(indices);
    updateVBO(verticesBuffer, indicesBuffer);
  }
  
  private void updateVBO(FloatBuffer vertices, ByteBuffer indices) {
    _vertices = vertices.remaining();
    
    //_va.bind(() -> {
      _vb.bind(() -> {
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 20, 0);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 20, 12);
      });
    //});
    
    if(indices != null) {
      _indices = indices.remaining();
      
      _ib.bind(() -> {
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
      });
    }
  }
  
  @Override public void destroy() {
    _vb.destroy();
    _ib.destroy();
    //_va.destroy();
  }
  
  @Override protected void drawImpl() {
    //_va.bind(() -> {
      GL20.glEnableVertexAttribArray(0);
      GL20.glEnableVertexAttribArray(1);
      
      if(_indices != 0) {
        _ib.bind(() -> {
          GL11.glDrawElements(GL11.GL_TRIANGLES, _indices, GL11.GL_UNSIGNED_BYTE, 0);
        });
      } else {
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, _vertices);
      }
      
      GL20.glDisableVertexAttribArray(1);
      GL20.glDisableVertexAttribArray(0);
    //});
  }
}