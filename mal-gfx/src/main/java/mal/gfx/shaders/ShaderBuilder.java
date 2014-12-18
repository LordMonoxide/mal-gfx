package mal.gfx.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderBuilder {
  private final ShaderManager _manager;
  
  private final ShaderStage _vsh = new ShaderStage();
  private final ShaderStage _fsh = new ShaderStage();
  
  public ShaderBuilder(ShaderManager manager) {
    _manager = manager;
    
    _vsh
      .addVariable("uniform mat4", "in_proj")
      .addVariable("uniform mat4", "in_view")
      .addVariable("uniform mat4", "in_model")
      .addVariable("attribute vec2", "tex");
    
    _vsh._main
      .addLine("gl_TexCoord[0] = vec4(tex, 0, 0);")
      .addLine("gl_Position = in_proj * in_view * gl_Vertex;");
    
    _fsh.addVariable("uniform sampler2D", "texture");
    _fsh._main.addLine("gl_FragColor = texture2D(texture, gl_TexCoord[0].st);");
  }
  
  public Shader build() {
    String vsh = _vsh.build();
    String fsh = _fsh.build();
    
    int vID = createShader(GL20.GL_VERTEX_SHADER, vsh);
    int fID = createShader(GL20.GL_FRAGMENT_SHADER, fsh);
    int pID = linkShader(vID, fID);
    
    return new Shader(pID, _manager);
  }
  
  private int createShader(int type, String source) {
    int id = GL20.glCreateShader(type);
    
    GL20.glShaderSource(id, source);
    GL20.glCompileShader(id);
    
    if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      int size = GL20.glGetShaderi(id, GL20.GL_INFO_LOG_LENGTH);
      String error = GL20.glGetShaderInfoLog(id, size);
      System.out.println("Error compiling shader:\n" + error);
    }
    
    return id;
  }
  
  private int linkShader(int vID, int fID) {
    int id = GL20.glCreateProgram();
    
    GL20.glAttachShader(id, vID);
    GL20.glAttachShader(id, fID);
    GL20.glBindAttribLocation(id, 1, "tex");
    GL20.glLinkProgram(id);
    
    if(GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
      int size = GL20.glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH);
      String error = GL20.glGetProgramInfoLog(id, size);
      System.out.println("Error linking shader:\n" + error);
    }
    
    return id;
  }
}