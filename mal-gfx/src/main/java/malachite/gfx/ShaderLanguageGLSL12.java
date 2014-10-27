package malachite.gfx;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import malachite.gfx.ShaderBuilderGLSL12.VARIABLE_MODE;
import malachite.gfx.ShaderBuilderGLSL12.Variable;
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
  
  @Override public void version(ShaderBuilderGLSL12.StageBuilder builder) {
    builder.raw("#version 1.20"); //$NON-NLS-1$
  }
  
  @Override public void variable(ShaderBuilderGLSL12 builder, Variable variable) {
    if(variable.mode.contains(VARIABLE_MODE.IN)) {
      builder.vsh.raw("uniform " + variable.type + " in_" + variable.name + ';'); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    if(variable.mode.contains(VARIABLE_MODE.OUT)) {
      builder.fsh.raw("varying " + variable.type + " out_" + variable.name + ';'); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    if(variable.mode.contains(VARIABLE_MODE.PASS)) {
      builder.vsh.raw("varying " + variable.type + " pass_" + variable.name + ';'); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }
  
  @Override public void function(ShaderBuilderGLSL12.StageBuilder builder, ShaderBuilderGLSL12.StageBuilder.Function function) {
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
  
  @Override public void finalizeVSH(ShaderBuilderGLSL12.StageBuilder builder) {
    builder.main().raw("gl_Position=in_pos;");
  }
  
  @Override public void finalizeFSH(ShaderBuilderGLSL12.StageBuilder builder) {
    builder.main().raw("gl_FrontColour=in_col;");
  }
}