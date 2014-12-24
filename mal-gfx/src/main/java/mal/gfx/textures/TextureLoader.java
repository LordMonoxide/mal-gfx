package mal.gfx.textures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.matthiasmann.twl.utils.PNGDecoder;

public class TextureLoader {
  //TODO: settings
  private static final String TEXTURES_DIR = "../gfx/textures/"; //$NON-NLS-1$
  
  private final Map<String, Texture> _textures = new ConcurrentHashMap<>();
  
  public Texture getTexture(String name, int w, int h, ByteBuffer data) {
    if(_textures.containsKey(name)) {
      return _textures.get(name);
    }
    
    Texture texture = new StandardTexture(w, h, data);
    _textures.put(name, texture);
    
    return texture;
  }
  
  public Texture getTexture(String file) {
    if(_textures.containsKey(file)) {
      return _textures.get(file);
    }
    
    ByteBuffer data = null;
    
    int w, h;
    
    File f = new File(TEXTURES_DIR + file);
    
    try {
      try(InputStream in = new FileInputStream(f)) {
        PNGDecoder png = new PNGDecoder(in);
        
        w = png.getWidth();
        h = png.getHeight();
        
        data = ByteBuffer.allocateDirect(4 * w * h);
        png.decode(data, w * 4, PNGDecoder.Format.RGBA);
        data.flip();
      }
    } catch(FileNotFoundException e) {
      System.err.println("Couldn't find texture \"" + file + '\"'); //$NON-NLS-1$
      return new TextureNotFound();
    } catch(IOException e) {
      System.err.println("Error loading texture \"" + file + '\"'); //$NON-NLS-1$
      e.printStackTrace();
      return new TextureNotFound();
    }
    
    StandardTexture texture = new StandardTexture(w, h, data);
    _textures.put(file, texture);
    
    return texture;
  }
}