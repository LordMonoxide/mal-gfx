package mal.gfx;

import org.lwjgl.util.vector.Vector3f;

import mal.gfx.shaders.Shader;
import mal.gfx.textures.Texture;

public abstract class TexturedDrawable {
  public final Vector3f pos = new Vector3f();
  
  protected MatrixStack _matrices;
  protected Texture _texture;
  protected Shader _shader;
  
  public TexturedDrawable(float[] vertices, MatrixStack matrices, Texture texture, Shader shader) {
    _matrices = matrices;
    _texture = texture;
    _shader = shader;
  }
  
  public TexturedDrawable(float[] vertices, byte[] indices, MatrixStack matrices, Texture texture, Shader shader) {
    _matrices = matrices;
    _texture = texture;
    _shader = shader;
  }
  
  public Texture getTexture() {
    return _texture;
  }
  
  public void setTexture(Texture texture) {
    _texture = texture;
  }
  
  public Shader getShader() {
    return _shader;
  }
  
  public void setShader(Shader shader) {
    _shader = shader;
  }
  
  public abstract void destroy();
  public abstract void draw();
}