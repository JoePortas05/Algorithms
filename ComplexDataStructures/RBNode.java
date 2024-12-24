public class RBNode {

  Integer key;
  RBNode left;
  RBNode right;
  RBNode p;
  boolean color; // Red = 0, Black = 1

  public RBNode(int key) {
    this.key = key;
    this.left = null;
    this.right = null;
    this.p = null;
    this.color = false;
  }

  public RBNode() {
    this.key = null;
    this.left = this;
    this.right = this;
    this.p = this;
    this.color = true;
  }

  public String printColor() {
    if(color) {
      return "BLACK";
    }
    return "RED";
  }

  public String display(RBNode x, int spaces) {
    StringBuilder builder = new StringBuilder();
    if(x.key == null) {
      builder.append("-".repeat(Math.max(0, spaces)));
      builder.append("NIL | BLACK");
      return builder.toString();
    }
    builder.append("-".repeat(Math.max(0, spaces)));
    builder.append(x.key).append(" | ").append(x.printColor()).append(System.lineSeparator());
    builder.append(display(x.left, spaces + 4)).append(System.lineSeparator());
    builder.append(display(x.right, spaces + 4));
    return builder.toString();
  }
}
