package malachite.gfx.interfaces;

import java.util.ArrayList;
import java.util.List;

import malachite.gfx.Shader;
import malachite.gfx.ShaderBuilderGLSL15;

public abstract class ShaderBuilder {
  public static void main(String[] args) {
    new ShaderBuilderGLSL15().build();
  }
  
  protected abstract String version();
  protected abstract String variable(VARIABLE_MODE direction, String type, String name);
  protected abstract String function(String type, String name, ShaderFunctionCallback callback, String... args);
  
  public Shader build() {
    StringBuilder vsh = new StringBuilder()
      .append(version()).append('\n')
      
      .append(variable(VARIABLE_MODE.IN, "vec4", "in_Pos")).append('\n')
      .append(variable(VARIABLE_MODE.IN, "vec4", "in_Col")).append('\n')
      
      .append(variable(VARIABLE_MODE.OUT, "vec4", "pass_Col")).append('\n')
      
      .append(function("void", "main", builder -> {
        builder.raw("gl_Position=in_Pos;")
               .raw("pass_Col=in_Col;");
      })).append('\n');
    
    System.out.println(vsh.toString());
    
    return null;
  }
  
  public enum VARIABLE_MODE {
    IN, OUT;
  }
  
  public class ShaderFunctionBuilder {
    private List<String> _line = new ArrayList<>();
    
    public ShaderFunctionBuilder raw(String code) {
      _line.add(code);
      return this;
    }
    
    public String build() {
      StringBuilder code = new StringBuilder();
      
      for(String line : _line) {
        code.append(line).append('\n');
      }
      
      return code.toString();
    }
  }
  
  public interface ShaderFunctionCallback {
    public void run(ShaderFunctionBuilder builder);
  }
}