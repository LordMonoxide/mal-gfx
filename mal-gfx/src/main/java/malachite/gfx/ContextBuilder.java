package malachite.gfx;

import java.util.Objects;

import malachite.gfx.providers.Providers;

import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 * <p>Factory class for building {@link Context}s.
 * All parameters are given sensible defaults, so it is
 * not usually necessary to use this class at all.</p>
 * <p><b>This class is intended for use only with {@link WindowBuilder}.</b></p>
 * @author Corey Frenette
 * @see WindowBuilder#withContextBuilder(ContextBuilder)
 */
public class ContextBuilder {
  private Providers _providers = new Providers();
  private PixelFormat _format = new PixelFormat();
  private ContextAttribs _attribs = new ContextAttribs();
  private boolean _blending = true;
  private int _fps = 60;
  
  protected ContextBuilder() { }
  
  /**
   * Sets the {@link Providers} to be used by the constructed {@link Context}.
   * @param providers a non-null instance of the {@link Providers} class
   * @return this
   */
  public ContextBuilder withProviders(Providers providers) {
    _providers = Objects.requireNonNull(providers);
    return this;
  }
  
  /**
   * Sets the {@link PixelFormat} to be used by the constructed {@link Context}.
   * @param format a non-null instance of the {@link PixelFormat} class
   * @return this
   */
  public ContextBuilder withPixelFormat(PixelFormat format) {
    _format = Objects.requireNonNull(format);
    return this;
  }

  /**
   * Sets the {@link ContextAttribs} to be used by the constructed {@link Context}.
   * @param attribs a non-null instance of the {@link ContextAttribs} class
   * @return this
   */
  public ContextBuilder withContextAttribs(ContextAttribs attribs) {
    _attribs = Objects.requireNonNull(attribs);
    return this;
  }

  /**
   * Controls whether or not the constructed {@link Context} will have alpha blending enabled.
   * @param blending enable/disable blending
   * @return this
   */
  public ContextBuilder withBlending(boolean blending) {
    _blending = blending;
    return this;
  }

  /**
   * Sets the frame rate limit for the constructed {@link Context}.
   * @param fps the frame rate limit, or 0 for unlimited
   * @return this
   */
  public ContextBuilder withFPSLimit(int fps) {
    _fps = fps;
    return this;
  }

  final PixelFormat format() {
    return _format;
  }
  
  final ContextAttribs attribs() {
    return _attribs;
  }
  
  final Context build(int w, int h) {
    if(_blending) {
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    return new Context<>(_fps, _providers);
  }
}