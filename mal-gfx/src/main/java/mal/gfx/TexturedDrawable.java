package mal.gfx;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import mal.gfx.shaders.Shader;
import mal.gfx.shaders.Uniform;
import mal.gfx.textures.Texture;

public abstract class TexturedDrawable {
  public final Vector3f pos = new Vector3f();
  
  private final List<UniformBinding<?>> _bindings = new ArrayList<>();
  
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
  
  public <T> void addUniformBinding(Uniform uniform, T binding) {
    _bindings.add(new UniformBinding<>(uniform, binding));
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
  
  public final void draw() {
    _matrices.push(() -> {
      _matrices.translate(pos);
      
      _shader.use();
      _shader.model.set(_matrices.getBuffer());
      
      for(UniformBinding<?> binding : _bindings) {
        binding.update();
      }
      
      _texture.use();
      
      drawImpl();
    });
  }
  
  public abstract void destroy();
  protected abstract void drawImpl();
  
  private class UniformBinding<T> {
    private final Uniform _uniform;
    private final T       _binding;
    
    public UniformBinding(Uniform uniform, T binding) {
      _uniform = uniform;
      _binding = binding;
    }
    
    //TODO: something better here?
    public void update() {
      if(_binding instanceof Vector2f) {
        _uniform.set((Vector2f)_binding);
      }
    }
  }
}