public class Vertex {
  int h;
  int e;
  int item;
  AList<Edge> adj;
  Vertex current;

  public Vertex(int item) {
    this.item = item;
    this.h = 0;
    this.e = 0;
    this.adj = new AList<>();
    this.current = null;
  }

  public boolean equals(Object o) {
    if(o instanceof Vertex v) {
      return v.item == this.item;
    }
    return false;
  }

  public String print() {
    return "Item " + this.item + " with height " + this.h + " and an outgoing flow of " + this.e;
  }
}