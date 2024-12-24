import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

class SNode {
  int val;
  SNode next;
  SNode below;

  public SNode(int val) {
    this.val = val;
    this.next = null;
    this.below = null;
  }

  public int horizLength() {
    if(this.next == null) {
      return 0;
    }
    return 1 + this.next.horizLength();
  }
}

public class SkipList {
  SNode topLeft;
  int maxHeight;
  int curHeight;
  Random rand;

  public SkipList(int maxHeight) {
    this.topLeft = null;
    this.maxHeight = maxHeight;
    this.curHeight = 0;
    this.rand = new Random();

    if(topLeft == null) {
      topLeft = new SNode((int)Double.NEGATIVE_INFINITY);
      SNode cur = topLeft;
      for(int i = this.maxHeight; i >= 0; i--) {
        cur.below = new SNode((int)Double.NEGATIVE_INFINITY);
        cur = cur.below;
      }
    }
  }

  private Integer getAmountOfLayers() {
    int count = 1;
    while(count < maxHeight && rand.nextBoolean()) {
      count++;
    }
    return count;
  }

  public SNode find(int val) {
    SNode cur = topLeft;
    while(cur != null) {
      if(val == cur.val) {
        return cur;
      }
      if(cur.next != null && val >= cur.next.val) {
        cur = cur.next;
      }
      else if(cur.below != null) {
        cur = cur.below;
      }
      else {
        cur = null;
      }
    }
    return null;
  }

  public void delete(int val) {
    SNode cur = topLeft;
    while(cur != null) {
      SNode nextLayerToRemoveFrom = cur.below;
      SNode prev = null;
      while(cur != null) {
        if(cur.val == val) {
          assert prev != null;
          prev.next = cur.next;
        }
        prev = cur;
        cur = cur.next;
      }
      cur = nextLayerToRemoveFrom;
    }
  }

  public void insert(int val, BufferedWriter fileWriter) throws IOException {
    int layersToInsert = getAmountOfLayers() - 1;
    fileWriter.write("Inserting " + val + " at " + (layersToInsert+1) + " layer");
    if(layersToInsert > 0) {
      fileWriter.write("s");
    }
    fileWriter.write(".\n");
    this.curHeight = Math.max(curHeight, layersToInsert);

    SNode cur = topLeft;
    for(int i = maxHeight; i >= layersToInsert; i--) {
      cur = cur.below;
    }

    SNode prevInserted = null;
    while(cur != null) {
      SNode nextLayerToInsertAt = cur.below;
      while(cur != null) {
        if(cur.val <= val && (cur.next == null || cur.next.val >= val)) {
          SNode newNode = new SNode(val);
          newNode.next = cur.next;
          cur.next = newNode;
          if(prevInserted != null) {
            prevInserted.below = newNode;
          }
          prevInserted = newNode;
          break;
        }
        else {
          cur = cur.next;
        }
      }
      cur = nextLayerToInsertAt;
      }
    }

  public String displayList() {
    StringBuilder builder = new StringBuilder();
    if(topLeft == null) {
      return "Empty list!";
    }
    SNode cur = topLeft;
    for(int i = maxHeight; i >= curHeight; i--) {
      cur = cur.below;
      if(cur == null) {
        return "";
      }
    }

    SNode lowLeft = cur;
    while(lowLeft.below != null) {
      lowLeft = lowLeft.below;
    }

    while(cur != null) {
      SNode toBeCur = cur.below;
      SNode lowLeftCopy = lowLeft;
      int spaces = 0;
      while(cur != null) {
        while(lowLeftCopy.val != cur.val) {
          lowLeftCopy = lowLeftCopy.next;
          spaces++;
        }
        builder.append("-".repeat(Math.max(1, spaces - 1)));
        if(cur.val == (int)Double.NEGATIVE_INFINITY) {
          builder.append(">").append("-inf");
        } else {
          builder.append(">").append(cur.val);
        }

        cur = cur.next;
      }
      builder.append(System.lineSeparator());
      builder.append(System.lineSeparator());
      cur = toBeCur;
    }
    return builder.toString();
  }
}