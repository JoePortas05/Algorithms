import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {

    String filePath = "src/input.txt";
    String delim = ", ";
    Graph G = Graph.generateGraph(filePath, delim);

    System.out.println("Before Relabel:\n");
    Graph.Print(G);
    System.out.println();


    Graph residual = Graph.GenericPushRelabel(G);

    System.out.println("Residual Graph:\n");
    Graph.Print(residual);
    System.out.println();
    System.out.println("The max flow is: " + Graph.getFlow(residual));
  }
}
