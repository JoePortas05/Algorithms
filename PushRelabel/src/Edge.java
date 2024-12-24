public class Edge {
  Vertex u;
  Vertex v;
  int c;
  int f;

  public Edge(Vertex u, Vertex v, int f, int c) {
    this.u = u;
    this.v = v;
    this.f = f;
    this.c = c;
    u.adj.add(this);
  }

  public boolean equals(Object o) {
    if(o instanceof Edge e) {
      return this.u.equals(e.u) && this.v.equals(e.v);
    }
    return false;
  }

  public String print() {
    return "From " + this.u.item + " to " + this.v.item
        + " flowing " + this.f + "/" + this.c;
  }
}