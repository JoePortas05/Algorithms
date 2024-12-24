import java.util.Stack;

class BHNode {

  int key;
  int degree;
  BHNode p;
  BHNode sibling;
  BHNode child;

  public BHNode(int key) {
    this.key = key;
    this.p = null;
    this.sibling = null;
    this.child = null;
    this.degree = 0;
  }

  public static String display(BHNode x) {
    if(x == null) {
      return "NULL";
    }
    else {
      return x.key +
              "  " + BHNode.display(x.sibling) +
              "\n" + BHNode.display(x.child);
    }
  }
}

public class BiHeap {

  BHNode head;

  public BiHeap() {
    this.head = null;
  }

  public static BiHeap makeBinomialHeap() {
    return new BiHeap();
  }

  public static void binomialHeapInsert(BiHeap H, BHNode x) {
    BiHeap Hp = makeBinomialHeap();
    x.p = null;
    x.child = null;
    x.sibling = null;
    x.degree = 0;
    Hp.head = x;
    BiHeap temp = binomialHeapUnion(H, Hp);
    H.head = temp.head;
  }

  public static BHNode minimum(BiHeap H) {
    BHNode y = null;
    BHNode x = H.head;
    int min = Integer.MAX_VALUE;
    while(x != null) {
      if(x.key < min) {
        min = x.key;
        y = x;
      }
      x = x.sibling;
    }
    return y;
  }

  public static void binomialLink(BHNode y, BHNode z) {
    y.p = z;
    y.sibling = z.child;
    z.child = y;
    z.degree += 1;
  }

  public static BHNode binomialHeapExtractMin(BiHeap H) {
    // Find root x w/min key in root list of H, remove x from root list of H
    BHNode x = null;
    int min = Integer.MAX_VALUE;
    BHNode cur = H.head;

    while(cur != null) {
      if(cur.key < min) {
        min = cur.key;
        x = cur;
      }
      cur = cur.sibling;
    }

    if(x == null) {
      return x;
    }
    
    if(x == H.head) {
      H.head = x.sibling;
    }
    else {
      cur = H.head.sibling;
      BHNode prev = H.head;

      while(cur != null) {
        if(cur == x) {
          prev.sibling = cur.sibling;
          break;
        }
        cur = cur.sibling;
        prev = prev.sibling;
      }
    }

    BiHeap Hp = makeBinomialHeap();

    // Reverse the order of the linked list of x's children and set Hp.head = head of resulting list
    cur = x.child;
    Stack<BHNode> stack = new Stack<>();
    while(cur != null) {
      stack.push(cur);
      cur = cur.sibling;
    }
    BHNode rev = stack.pop();
    Hp.head = rev;

    while(!stack.isEmpty()) {
      BHNode sib = stack.pop();
      rev.sibling = sib;
      rev = sib;
    }
    rev.sibling = null;

    BiHeap newHeap = binomialHeapUnion(H, Hp);
    H.head = newHeap.head;
    return x;
  }

  public static BiHeap binomialHeapUnion(BiHeap H1, BiHeap H2) {
    BiHeap H = makeBinomialHeap();
    H.head = binomialHeapMerge(H1, H2);
    if(H.head == null) {
      return H;
    }
    BHNode prevX = null;
    BHNode x = H.head;
    BHNode nextX = x.sibling;
    while(nextX != null) {
      if(x.degree != nextX.degree || (nextX.sibling != null && nextX.sibling.degree == x.degree)) {
        prevX = x;
        x = nextX;
      }
      else if(x.key <= nextX.key) {
        x.sibling = nextX.sibling;
        binomialLink(nextX, x);
      }
      else if(prevX == null) {
        H.head = nextX;
        binomialLink(x, nextX);
        x = nextX;
      }
      else {
          prevX.sibling = nextX;
          binomialLink(x, nextX);
          x = nextX;
      }
      nextX = x.sibling;
    }
    return H;
  }

  public static BHNode binomialHeapMerge(BiHeap H1, BiHeap H2) {
    if(H1 == null && H2 == null) {
      return null;
    }
    if(H1 == null) {
      return H2.head;
    }
    if(H2 == null) {
      return H1.head;
    }

    BHNode head1 = H1.head;
    BHNode head2 = H2.head;

    if(head1 == null && head2 == null) {
      return null;
    }
    if(head1 == null) {
      return head2;
    }
    if(head2 == null) {
      return head1;
    }

    BHNode toRet;
    if(head1.degree < head2.degree) {
      toRet = head1;
      head1 = head1.sibling;
    }
    else {
      toRet = head2;
      head2 = head2.sibling;
    }
    BHNode cur = toRet;

    while(true) {
      if(head1 == null) {
        cur.sibling = head2;
        return toRet;
      }
      if(head2 == null) {
        cur.sibling = head1;
        return toRet;
      }
      if(head1.degree < head2.degree) {
        cur.sibling = head1;
        head1 = head1.sibling;
      }
      else {
        cur.sibling = head2;
        head2 = head2.sibling;
      }
      cur = cur.sibling;
    }
  }

  public static void binomialHeapDecreaseKey(BiHeap H, BHNode x, int k) {
    if(k > x.key) {
      throw new IllegalArgumentException("New key is greater than current key");
    }
    x.key = k;
    BHNode y = x;
    BHNode z = y.p;
    while(z != null && y.key < z.key) {
      int tmp = y.key;
      y.key = z.key;
      z.key = tmp;
      y = z;
      z = y.p;
    }
  }

  public static BHNode binomialHeapFind(BiHeap H, BHNode x, int key) {
    if(x == null) {
      return null;
    }
    if(x.key == key) {
      return x;
    }
    BHNode sib = binomialHeapFind(H, x.sibling, key);
    BHNode child = binomialHeapFind(H, x.child, key);
    if(sib != null) {
      return sib;
    }
    return child;
  }

  public static String binomialHeapDisplay(BiHeap H) {
    if(H.head == null) {
      return "Empty Heap!";
    }
    return BHNode.display(H.head);
  }

  public static void binomialHeapDelete(BiHeap H, BHNode x) {
    binomialHeapDecreaseKey(H, x, Integer.MIN_VALUE);
    binomialHeapExtractMin(H);
  }
}