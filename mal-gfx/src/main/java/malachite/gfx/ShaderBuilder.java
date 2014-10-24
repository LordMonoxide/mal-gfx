package malachite.gfx;

import java.util.ArrayList;
import java.util.List;

import malachite.gfx.interfaces.ShaderLanguage;

public class ShaderBuilder {
  private final ShaderLanguage _language;
  
  private final List<Variable> _variables = new ArrayList<>();
  private final List<Function> _functions = new ArrayList<>();
  
  private StringBuilder _code;
  
  ShaderBuilder(ShaderLanguage language) {
    _language = language;
  }
  
  public Shader build() {
    _code = new StringBuilder();
    
    _language.version(this);
    
    for(Variable variable : _variables) {
      _language.variable(this, variable);
    }
    
    for(Function function : _functions) {
      _language.function(this, function);
    }
    
    System.out.println(_code.toString());
    
    return null;
  }
  
  public ShaderBuilder raw(String code) {
    _code.append(code).append('\n');
    return this;
  }
  
  public ShaderBuilder variable(VARIABLE_MODE mode, String type, String name) {
    _variables.add(new Variable(mode, type, name));
    return this;
  }
  
  public FunctionBuilder function(String type, String name) {
    return new FunctionBuilder(this, type, name);
  }
  
  public class FunctionBuilder {
    private final ShaderBuilder _sb;
    
    private final List<Argument> _args = new ArrayList<>();
    private final List<String>   _line = new ArrayList<>();
    
    private String _type;
    private String _name;
    
    private FunctionBuilder(ShaderBuilder sb, String type, String name) {
      _sb   = sb;
      _type = type;
      _name = name;
    }
    
    public FunctionBuilder argument(String type, String name) {
      _args.add(new Argument(type, name));
      return this;
    }
    
    public FunctionBuilder raw(String code) {
      _line.add(code);
      return this;
    }
    
    public ShaderBuilder build() {
      _functions.add(new Function(
        _type, _name,
        _args.toArray(new Argument[0]),
        _line.toArray(new String  [0])
      ));
      
      return _sb;
    }
  }
  
  public class Variable {
    public final VARIABLE_MODE mode;
    public final String        type;
    public final String        name;
    
    private Variable(VARIABLE_MODE mode, String type, String name) {
      this.mode = mode;
      this.type = type;
      this.name = name;
    }
  }
  
  public class Function {
    public final String     type;
    public final String     name;
    public final Argument[] args;
    public final String  [] line;
    
    private Function(String type, String name, Argument[] args, String[] line) {
      this.type = type;
      this.name = name;
      this.args = args;
      this.line = line;
    }
  }
  
  public class Argument {
    public final String type;
    public final String name;
    
    private Argument(String type, String name) {
      this.type = type;
      this.name = name;
    }
  }
  
  public interface FunctionCallback {
    public void run(Function builder);
  }
  
  public enum VARIABLE_MODE {
    IN, OUT, PASS;
  }
}