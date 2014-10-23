package malachite.gfx;

import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import malachite.gfx.interfaces.Shader;

public class ShaderGLSL15 implements Shader {
  public static boolean test() {
    ContextCapabilities caps = GLContext.getCapabilities();
    
    // 3.0 is guaranteed to support GLSL 1.50
    if(caps.OpenGL30) {
      return true;
    }
    
    // Grab the version string, remove the vendor-specific info,
    // split the version by the decimal, and jam it into an int
    String[] versionString = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION).split(" ")[0].split("\\.");
    int version = Integer.parseInt(versionString[0]) * 100 + Integer.parseInt(versionString[1]);
    
    // Return true if we have at least version 1.50, false otherwise
    return version >= 150;
  }
  
  @Override public String version() {
    return "#version 1.50";
  }
  
  @Override public String variable(VARIABLE_MODE direction, String type, String name) {
    StringBuilder ret = new StringBuilder()
       .append(direction == VARIABLE_MODE.IN ? "in " : "out ")
       .append(type).append(' ')
       .append(name);
    return ret.toString();
  }
  
  @Override public String function(String type, String name, String... args) {
    StringBuilder ret = new StringBuilder()
       .append(type).append(' ')
       .append(name).append('(');
    
    for(int i = 0; i < args.length; i++) {
      ret.append(args[i]);
      
      if(i < args.length - 1) {
        ret.append(',');
      }
    }
    
    ret.append(')');
    
    return null;
  }
}