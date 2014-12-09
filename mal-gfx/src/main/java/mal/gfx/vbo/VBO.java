package mal.gfx.vbo;

class VBO {
  private VBO() { }
  
  static VertexArray createVertexArray() {
    return new VertexArray();
  }
  
  static VertexBuffer createVertexBuffer() {
    return new VertexBuffer();
  }
  
  static IndexBuffer createIndexBuffer() {
    return new IndexBuffer();
  }
  
  static Buffer createNoOpBuffer() {
    return new Buffer(0) {
      @Override public void destroy() { }      
      @Override public void bind(Runnable r) { }
    };
  }
}