package malachite.gfx;

import java.util.ArrayList;
import java.util.List;

public class ShaderBuilder {
  private final Context<?> _ctx;
  
  private final List<Variable> _variables = new ArrayList<>();
  private final List<Function> _functions = new ArrayList<>();
  
  ShaderBuilder(Context<?> ctx) {
    _ctx = ctx;
  }
  
  public Shader build() {
    
    
    return null;
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
    private final VARIABLE_MODE _mode;
    private final String        _type;
    private final String        _name;
    
    private Variable(VARIABLE_MODE mode, String type, String name) {
      _mode = mode;
      _type = type;
      _name = name;
    }
  }
  
  public class Function {
    private final String     _type;
    private final String     _name;
    private final Argument[] _args;
    private final String  [] _line;
    
    private Function(String type, String name, Argument[] args, String[] line) {
      _type = type;
      _name = name;
      _args = args;
      _line = line;
    }
  }
  
  private class Argument {
    private final String _type;
    private final String _name;
    
    private Argument(String type, String name) {
      _type = type;
      _name = name;
    }
  }
  
  public interface FunctionCallback {
    public void run(Function builder);
  }
  
  public enum VARIABLE_MODE {
    IN, OUT, PASS;
  }
}