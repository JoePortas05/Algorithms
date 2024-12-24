import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class Graph {
  public AList<Vertex> V;
  public AList<Edge> E;
  public Vertex s;
  public Vertex t;
  private String filePath;
  private String delim;
  private final AList<Edge> residualEdges;
  private final AList<Edge> allEdges;

  public Graph() {
    this.V = new AList<>();
    this.E = new AList<>();
    this.s = null;
    this.t = null;
    this.filePath = null;
    this.delim = null;
    this.residualEdges = new AList<>();
    this.allEdges = new AList<>();
  }

  public static Graph InitializePreFlow(Graph graph, Vertex s) throws IOException {
    Graph G = Graph.generateGraph(graph.filePath, graph.delim);

    for(Vertex v : G.V) {
      v.h = 0;
      v.e = 0;
    }

    for(Edge e : G.s.adj) {
      if(G.E.contains(e)) {
        e.f = e.c;
        e.v.e = e.c;
        G.allEdges.find(new Edge(e.v, e.u, 0, 0)).c = e.f;
      }
    }

    G.s.h = G.V.size();
    return G;
  }

  public static boolean Push(Graph G, Edge e) {
    int cf = e.c - e.f;

    if(e.u.e <= 0 || cf <= 0 || e.u.h != e.v.h + 1) {
      return false;
    }

    int df = Math.min(e.u.e, cf);

    e.f += df;
    G.allEdges.find(new Edge(e.v, e.u, 1, 1)).f -= df;

    e.u.e -= df;
    e.v.e += df;
    return true;
  }

  public static boolean Relabel(Vertex u) {
    if(u.e <= 0) {
      return false;
    }
    for(Edge e : u.adj) {
      int cf = e.c - e.f;
      if(u.h > e.v.h && cf > 0) {
        return false;
      }
    }

    int min = Integer.MAX_VALUE;
    for(Edge e : u.adj) {
      int cf = e.c - e.f;
      if(e.v.h < min && cf > 0) {
        min = e.v.h;
      }
    }
    u.h = 1 + min;
    return true;
  }

  private static Boolean loop(Graph G) {
    for(Edge e : G.E) {
      if(Graph.Push(G, e)) {
        return true;
      }
    }
    for(Vertex V : G.V) {
      if(Graph.Relabel(V)) {
        return true;
      }
    }
    return false;
  }

  public static Graph GenericPushRelabel(Graph graph) throws IOException {
    Graph G = Graph.InitializePreFlow(graph, graph.s);
    boolean cont;
    do {
      cont = loop(G);
    } while (cont);
    return G;
  }

  public static int getFlow(Graph g){
    int flow = 0;
    for(Edge e : g.E) {
      if(e.v.equals(g.t)) {
        flow += e.f;
      }
    }
    return flow;
  }

    public static void Discharge(Graph G, Vertex u) {
    int index = -1;
    for(int i = 0; i < u.adj.size(); i++) {
      if(u.adj.get(i).v.equals(u.current)) {
        index = i;
      }
    }

    while(u.e > 0) {
      Vertex v = u.current;

      if(v == null) {
        Relabel(u);
        u.current = u.adj.get(0).v;
      }
      else {
        // Cannot define this earlier or e may be null and getting cf will error
        Edge e = u.adj.get(index);
        int cf = e.c - e.f;
        if(cf > 0 && e.u.h == e.v.h + 1) {
          Push(G, e);
        }
        else {
          u.current = u.adj.get(index + 1).v;
        }
      }
    }
  }

  public static void Print(Graph G) {
    System.out.println("Vertices: ");
    System.out.println();
    int i = 1;
    for(Vertex V : G.V) {
      if(i == 1) {
        System.out.println("Source: " + V.print());
      } else if(i == G.V.size()) {
        System.out.println("Sink: " + V.print());
      } else {
        System.out.println("Vertex " + i + ": " + V.print());
      }
      i++;
    }
    i = 1;
    System.out.println();
    System.out.println("Edges: ");
    System.out.println();
    for(Edge E : G.allEdges) {
      System.out.println("Edge " + i + ": " + E.print());
      i++;
    }
  }

  // Assumes vertices exist strictly with items [0:n-1]
  public static Graph generateGraph(String filePath, String delim) throws IOException {
    // Make the reader
    BufferedReader br = new BufferedReader(new FileReader(filePath));
    Graph g = new Graph();
    g.filePath = filePath;
    g.delim = delim;

    AList<int[]> rows = new AList<>();

    // Create a list of all the info
    String line;
    while((line = br.readLine()) != null) {
      String[] row = line.split(delim);
      int[] nums = new int[row.length];
      for(int i  = 0; i < row.length; i++) {
        nums[i] = Integer.parseInt(row[i]);
      }
      rows.add(nums);
    }
    br.close();

    // Add the vertices
    for(int[] row : rows) {
      Vertex v0 = new Vertex(row[0]);
      Vertex v1 = new Vertex(row[1]);
      if(!(g.V.contains(v0))) {
        g.V.add(v0);
      }
      if(!(g.V.contains(v1))) {
        g.V.add(v1);
      }

      // Put g.V in sorted order (helps with getting edges)
      g.V.sort(new Comparator<Vertex>() {
        @Override
        public int compare(Vertex o1, Vertex o2) {
          return Integer.compare(o1.item, o2.item);
        }
      });
    }

    // Add the edges
    for(int[] row : rows) {
      Edge e = new Edge(g.V.get(row[0]), g.V.get(row[1]), 0, row[2]);
      g.E.add(e);
      g.allEdges.add(e);
      e.u.adj.add(e);
    }

    // Add residual network
    for(Edge e : g.E) {
      Edge er = new Edge(e.v, e.u, 0, 0);
      if(!g.allEdges.contains(er)) {
        g.residualEdges.add(er);
        g.allEdges.add(er);
        e.v.adj.add(er);
      }
    }

    g.s = g.V.getFirst();
    g.t = g.V.getLast();

    return g;
  }
}