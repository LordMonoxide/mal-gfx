package mal.gfx;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public final class WindowEvents {
  WindowEvents() { }
  
  private Deque<CreateEvent>       _create = new ConcurrentLinkedDeque<>();
  private Deque<LoopEvent>         _loop = new ConcurrentLinkedDeque<>();
  
  private Deque<MoveEvent>         _move = new ConcurrentLinkedDeque<>();
  private Deque<ResizeEvent>       _resize = new ConcurrentLinkedDeque<>();
  private Deque<CloseEvent>        _close = new ConcurrentLinkedDeque<>();
  private Deque<RefreshEvent>      _refresh = new ConcurrentLinkedDeque<>();
  private Deque<FocusEvent>        _focus = new ConcurrentLinkedDeque<>();
  private Deque<FocusEvent>        _unfocus = new ConcurrentLinkedDeque<>();
  private Deque<IconifyEvent>      _iconify = new ConcurrentLinkedDeque<>();
  private Deque<IconifyEvent>      _uniconify = new ConcurrentLinkedDeque<>();
  private Deque<KeyDownEvent>      _keyDown = new ConcurrentLinkedDeque<>();
  private Deque<KeyUpEvent>        _keyUp = new ConcurrentLinkedDeque<>();
  private Deque<CharacterEvent>    _character = new ConcurrentLinkedDeque<>();
  private Deque<MouseButtonEvent>  _mouseDown = new ConcurrentLinkedDeque<>();
  private Deque<MouseButtonEvent>  _mouseUp = new ConcurrentLinkedDeque<>();
  private Deque<MouseMoveEvent>    _mouseMove = new ConcurrentLinkedDeque<>();
  private Deque<MouseHoverEvent>   _mouseHoverIn = new ConcurrentLinkedDeque<>();
  private Deque<MouseHoverEvent>   _mouseHoverOut = new ConcurrentLinkedDeque<>();
  private Deque<MouseScrollEvent>  _mouseScroll = new ConcurrentLinkedDeque<>();
  private Deque<BufferResizeEvent> _bufferResize = new ConcurrentLinkedDeque<>();
  private Deque<DropEvent>         _drop = new ConcurrentLinkedDeque<>();
  
  public WindowEvents onCreate(CreateEvent e) { _create.add(e); return this; }
  public WindowEvents onLoop(LoopEvent e) { _loop.add(e); return this; }
  
  public WindowEvents onMove(MoveEvent e) { _move.add(e); return this; }
  public WindowEvents onResize(ResizeEvent e) { _resize.add(e); return this; }
  public WindowEvents onClose(CloseEvent e) { _close.add(e); return this; }
  public WindowEvents onRefresh(RefreshEvent e) { _refresh.add(e); return this; }
  public WindowEvents onFocus(FocusEvent e) { _focus.add(e); return this; }
  public WindowEvents onUnFocus(FocusEvent e) { _unfocus.add(e); return this; }
  public WindowEvents onIconify(IconifyEvent e) { _iconify.add(e); return this; }
  public WindowEvents onUniconify(IconifyEvent e) { _uniconify.add(e); return this; }
  public WindowEvents onKeyDown(KeyDownEvent e) { _keyDown.add(e); return this; }
  public WindowEvents onKeyUp(KeyUpEvent e) { _keyUp.add(e); return this; }
  public WindowEvents onCharacter(CharacterEvent e) { _character.add(e); return this; }
  public WindowEvents onMouseDown(MouseButtonEvent e) { _mouseDown.add(e); return this; }
  public WindowEvents onMouseUp(MouseButtonEvent e) { _mouseUp.add(e); return this; }
  public WindowEvents onMouseMove(MouseMoveEvent e) { _mouseMove.add(e); return this; }
  public WindowEvents onMouseHoverIn(MouseHoverEvent e) { _mouseHoverIn.add(e); return this; }
  public WindowEvents onMouseHoverOut(MouseHoverEvent e) { _mouseHoverOut.add(e); return this; }
  public WindowEvents onMouseScroll(MouseScrollEvent e) { _mouseScroll.add(e); return this; }
  public WindowEvents onBufferResize(BufferResizeEvent e) { _bufferResize.add(e); return this; }
  public WindowEvents onDrop(DropEvent e) { _drop.add(e); return this; }
  
  void onCreate() {
    for(CreateEvent e : _create) { e.run(); }
  }
  
  void onLoop() {
    for(LoopEvent e : _loop) { e.run(); }
  }
  
  void onMove(int x, int y) {
    for(MoveEvent e : _move) { e.run(x, y); }
  }
  
  void onResize(int w, int h) {
    for(ResizeEvent e : _resize) { e.run(w, h); }
  }
  
  void onClose() {
    for(CloseEvent e : _close) { e.run(); }
  }
  
  void onRefresh() {
    for(RefreshEvent e : _refresh) { e.run(); }
  }
  
  void onFocus(int focused) {
    if(focused == GL11.GL_FALSE) {
      for(FocusEvent e : _unfocus) { e.run(); }
    } else {
      for(FocusEvent e : _focus) { e.run(); }
    }
  }
  
  void onIconify(int iconified) {
    if(iconified == GL11.GL_FALSE) {
      for(IconifyEvent e : _uniconify) { e.run(); }
    } else {
      for(IconifyEvent e : _iconify) { e.run(); }
    }
  }
  
  void onKeyPress(int key, int scancode, int action, int mods) {
    switch(action) {
      case GLFW_PRESS:
        for(KeyDownEvent e : _keyDown) { e.run(key, mods, false, scancode); }
      break;
      
      case GLFW_REPEAT:
        for(KeyDownEvent e : _keyDown) { e.run(key, mods, true, scancode); }
      break;
      
      case GLFW_RELEASE:
        for(KeyUpEvent e : _keyUp) { e.run(key, mods, scancode); }
      break;
    }
  }
  
  void onCharacter(int codepoint, int mods) {
    for(CharacterEvent e : _character) { e.run(codepoint, mods); }
  }
  
  void onMouseButton(int button, int action, int mods) {
    switch(action) {
      case GLFW_PRESS:
        for(MouseButtonEvent e : _mouseDown) { e.run(button, mods); }
      break;
      
      case GLFW_RELEASE:
        for(MouseButtonEvent e : _mouseUp) { e.run(button, mods); }
      break;
    }
  }
  
  void onMouseMove(double x, double y) {
    for(MouseMoveEvent e : _mouseMove) { e.run(x, y); }
  }
  
  void onMouseHover(int entered) {
    switch(entered) {
      case GL_TRUE:
        for(MouseHoverEvent e : _mouseHoverIn) { e.run(); }
      break;
      
      case GL_FALSE:
        for(MouseHoverEvent e : _mouseHoverOut) { e.run(); }
      break;
    }
  }
  
  void onMouseScroll(double xoffset, double yoffset) {
    for(MouseScrollEvent e : _mouseScroll) { e.run(xoffset, yoffset); }
  }
  
  void onBufferResize(int w, int h) {
    for(BufferResizeEvent e : _bufferResize) { e.run(w, h); }
  }
  
  void onDrop(int count, long names) {
    PointerBuffer nameBuffer = memPointerBuffer(names, count);
    
    String[] name = new String[count];
    for(int i = 0; i < count; i++) {
      name[i] = memDecodeUTF8(memByteBufferNT1(nameBuffer.get(i)));
    }
    
    for(DropEvent e : _drop) { e.run(name); } 
  }
  
  public interface CreateEvent       { public void run(); }
  public interface LoopEvent         { public void run(); }
  
  public interface MoveEvent         { public void run(int x, int y); }
  public interface ResizeEvent       { public void run(int w, int h); }
  public interface CloseEvent        { public void run(); }
  public interface RefreshEvent      { public void run(); }
  public interface FocusEvent        { public void run(); }
  public interface IconifyEvent      { public void run(); }
  public interface KeyDownEvent      { public void run(int key, int mods, boolean repeat, int code); }
  public interface KeyUpEvent        { public void run(int key, int mods, int code); }
  public interface CharacterEvent    { public void run(int codepoint, int mods); }
  public interface MouseButtonEvent  { public void run(int button, int mods); }
  public interface MouseMoveEvent    { public void run(double x, double y); }
  public interface MouseHoverEvent   { public void run(); }
  public interface MouseScrollEvent  { public void run(double x, double y); }
  public interface BufferResizeEvent { public void run(int w, int h); }
  public interface DropEvent         { public void run(String[] names); }
}