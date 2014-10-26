package malachite.gfx;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import malachite.gfx.interfaces.ShaderLanguage;

public class ShaderBuilder {
  public final StageBuilder vsh = new StageBuilder(this);
  public final StageBuilder fsh = new StageBuilder(this);
  
  private final ShaderLanguage _language;
  
  private final List<Variable> _variables = new ArrayList<>();
  
  ShaderBuilder(ShaderLanguage language) {
    _language = language;
  }
  
  /*public ShaderBuilder raw(String code) {
    _code.append(code).append('\n');
    return this;
  }*/
  
  public ShaderBuilder variable(EnumSet<VARIABLE_MODE> mode, String type, String name) {
    _variables.add(new Variable(mode, type, name));
    return this;
  }
  
  public Shader build() {
    vsh.init();
    fsh.init();
    
    _language.version(vsh);
    _language.version(fsh);
    
    _language.variable(this, new Variable(EnumSet.of(VARIABLE_MODE.IN),       "vec4", "pos"));
    _language.variable(this, new Variable(EnumSet.allOf(VARIABLE_MODE.class), "vec4", "col"));
    
    for(Variable variable : _variables) {
      _language.variable(this, variable);
    }
    
    String vshCode = vsh.build();
    String fshCode = fsh.build();
    
    System.out.println(vshCode);
    System.out.println(fshCode);
    
    return null;
  }
  
  public class StageBuilder {
    private final ShaderBuilder _sb;
    
    private final List<Function> _functions = new ArrayList<>();
    
    private final FunctionBuilder _main = new FunctionBuilder(this, "void", "main");
    
    private StringBuilder _code;
    
    private StageBuilder(ShaderBuilder sb) {
      _sb = sb;
    }
    
    private void init() {
      _code = new StringBuilder();
    }
    
    public StageBuilder raw(String code) {
      _code.append(code).append('\n');
      return this;
    }
    
    public FunctionBuilder function(String type, String name) {
      return new FunctionBuilder(this, type, name);
    }
    
    public FunctionBuilder main() {
      return _main;
    }
    
    public String build() {
      _language.finalizeVSH(this);
      _main.build();
      
      for(Function function : _functions) {
        _language.function(this, function);
      }
      
      return _code.toString();
    }
    
    public class FunctionBuilder {
      private final StageBuilder _sb;
      
      private final List<Argument> _args = new ArrayList<>();
      private final List<String>   _line = new ArrayList<>();
      
      private String _type;
      private String _name;
      
      private FunctionBuilder(StageBuilder sb, String type, String name) {
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
      
      public StageBuilder build() {
        _functions.add(new Function(
          _type, _name,
          _args.toArray(new Argument[0]),
          _line.toArray(new String  [0])
        ));
        
        return _sb;
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
  }
  
  public class Variable {
    public final EnumSet<VARIABLE_MODE> mode;
    
    public final String type;
    public final String name;
    
    private Variable(EnumSet<VARIABLE_MODE> mode, String type, String name) {
      this.mode = mode;
      this.type = type;
      this.name = name;
    }
  }
  
  public enum VARIABLE_MODE {
    IN, OUT, PASS;
  }
}