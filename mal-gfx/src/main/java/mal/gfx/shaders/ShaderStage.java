package mal.gfx.shaders;

import java.util.ArrayList;
import java.util.List;

public class ShaderStage {
  private List<ShaderVariable> _variables = new ArrayList<>();
  private List<ShaderFunction> _functions = new ArrayList<>();
  
  final ShaderFunction _main = new ShaderFunction("void", "main");
  
  public ShaderStage addVariable(String type, String name) {
    _variables.add(new ShaderVariable(type, name));
    return this;
  }
  
  public ShaderStage addFunction(String type, String name, ShaderFunctionCallback cb) {
    _main.addLine(name + "();");
    
    ShaderFunction fn = new ShaderFunction(type, name);
    cb.run(fn);
    
    _functions.add(fn);
    return this;
  }
  
  public String build() {
    StringBuilder s = new StringBuilder("#version 120\n");
    
    for(ShaderVariable v : _variables) {
      s.append(v.build()).append('\n');
    }
    
    for(ShaderFunction f : _functions) {
      s.append(f.build()).append('\n');
    }
    
    s.append(_main.build()).append('\n');
    
    return s.toString();
  }
  
  public class ShaderVariable {
    public final String type, name;
    
    public ShaderVariable(String type, String name) {
      this.type = type;
      this.name = name;
    }
    
    public String build() {
      return type + ' ' + name + ';';
    }
  }
  
  public class ShaderFunction {
    private List<String> _lines = new ArrayList<>();
    
    public final String type, name;
    
    public ShaderFunction(String type, String name) {
      this.type = type;
      this.name = name;
    }
    
    public ShaderFunction addLine(String code) {
      _lines.add(code);
      return this;
    }
    
    public String build() {
      StringBuilder s = new StringBuilder();
      
      s.append(type).append(' ').append(name).append("() {\n");
      
      for(String l : _lines) {
        s.append(l).append('\n');
      }
      
      s.append('}');
      
      return s.toString();
    }
  }
  
  public interface ShaderFunctionCallback {
    public void run(ShaderFunction fn);
  }
}