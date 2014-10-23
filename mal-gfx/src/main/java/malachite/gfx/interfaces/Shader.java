package malachite.gfx.interfaces;

public interface Shader {
  public String version();
  public String variable(VARIABLE_MODE direction, String type, String name);
  public String function(String type, String name, String... args);
  
  public enum VARIABLE_MODE {
    IN, OUT;
  }
}