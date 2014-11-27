package malachite.gfx;

import malachite.gfx.interfaces.ShaderBuilder;
import malachite.gfx.shaders.Fragment;
import malachite.gfx.shaders.Input;

public class ShaderBuilderGLSL150 extends ShaderBuilder {
  @Override public Shader build() {
    StringBuilder vsh = new StringBuilder()
      .append("#version 1.50")
      .append("in vec4 in_pos;")
      .append("void main() {")
        .append("gl_Position = in_pos;")
      .append('}');
    
    StringBuilder fsh = new StringBuilder()
      .append("#version 1.50")
      .append("out vec4 out_col;")
      .append("void main() {")
        .append("out_col = vec4(1, 1, 0, 1);")
      .append('}');
    
    return null;
  }
  
  @Override protected Fragment newRecolour() {
    return new Fragment() {
      @Override public Input[] inputs() {
        return new Input[] {
          //new Input("vec4", ", value)
        };
      }
      
      @Override public String[] modifiers() {
        return null;
      }
    };
  }
  
  @Override protected Fragment newDesaturate() {
    return null;
  }
}