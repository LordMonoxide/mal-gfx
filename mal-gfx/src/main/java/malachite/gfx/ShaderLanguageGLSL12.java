package malachite.gfx;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import malachite.gfx.ShaderBuilder.Variable;
import malachite.gfx.interfaces.ShaderLanguage;

public class ShaderLanguageGLSL12 implements ShaderLanguage {
  public static boolean test() {
    ContextCapabilities caps = GLContext.getCapabilities();
    
    // 2.1 is guaranteed to support GLSL 1.20
    if(caps.OpenGL21) {
      return true;
    }
    
    // Grab the version string, remove the vendor-specific info,
    // split the version by the decimal, and jam it into an int
    String[] versionString = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION).split(" ")[0].split("\\."); //$NON-NLS-1$ //$NON-NLS-2$
    int version = Integer.parseInt(versionString[0]) * 100 + Integer.parseInt(versionString[1]);
    
    // Return true if we have at least version 1.50, false otherwise
    return version >= 120;
  }
  
  @Override public void version(ShaderBuilder builder) {
    builder.raw("#version 1.20"); //$NON-NLS-1$
  }
  
  @Override public void variable(ShaderBuilder builder, Variable variable) {
    String direction = null,
           prefix    = null;
    
    switch(variable.mode) {
      case IN: 
        direction = "uniform";  //$NON-NLS-1$
        prefix    = "in_"; //$NON-NLS-1$
        break;
        
      case OUT:
        direction = "varying";  //$NON-NLS-1$
        prefix    = "out_"; //$NON-NLS-1$
        break;
        
      case PASS:
        builder.raw("varying " + variable.type + " in_" + variable.name + ';'); //$NON-NLS-1$ //$NON-NLS-2$
        return;
    }
    
    builder.raw(direction + ' ' + variable.type + ' ' + prefix + variable.name + ';');
  }
  
  @Override public void function(ShaderBuilder.StageBuilder builder, ShaderBuilder.StageBuilder.Function function) {
    StringBuilder ret = new StringBuilder()
      .append(function.type).append(' ')
      .append(function.name).append('(');
    
    for(int i = 0; i < function.args.length; i++) {
      ret.append(function.args[i]);
      
      if(i < function.args.length - 1) {
        ret.append(',');
      }
    }
    
    ret.append(") {\n"); //$NON-NLS-1$
    
    for(String line : function.line) {
      ret.append(line).append('\n');
    }
    
    ret.append('}');
    
    builder.raw(ret.toString());
  }
  
  @Override public void finalizeVSH(ShaderBuilder.StageBuilder builder) {
    builder.main().raw("gl_Position=in_pos;");
  }
  
  @Override public void finalizeFSH(ShaderBuilder.StageBuilder builder) {
    builder.main().raw("gl_FrontColour=in_col;");
  }
}