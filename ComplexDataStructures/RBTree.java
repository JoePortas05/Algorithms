public class RBTree {
  RBNode root;
  RBNode nil;

  public RBTree() {
    this.nil = new RBNode();
    this.root = this.nil;
  }

  public void LeftRotate(RBTree T, RBNode x) {
    RBNode y = x.right;
    x.right = y.left;
    if(y.left != T.nil) {
      y.left.p = x;
    }
    y.p = x.p;
    if (x.p.equals(T.nil)) {
      T.root = y;
    }
    else if (x.equals(x.p.left)) {
      x.p.left = y;
    }
    else {
      x.p.right = y;
    }
    y.left = x;
    x.p = y;
  }

  public void RightRotate(RBTree T, RBNode x) {
    RBNode y = x.left;
    x.left = y.right;
    if(y.right != T.nil) {
      y.right.p = x;
    }
    y.p = x.p;
    if (x.p.equals(T.nil)) {
      T.root = y;
    }
    else if (x.equals(x.p.right)) {
      x.p.right = y;
    }
    else {
      x.p.left = y;
    }
    y.right = x;
    x.p = y;
  }

  public void RBInsert(RBTree T, RBNode z) {
    RBNode x = T.root;
    RBNode y = T.nil;
    while(x != T.nil) {
      y = x;
      if(z.key < x.key) {
        x = x.left;
      }
      else {
        x = x.right;
      }
    }
    z.p = y;
    if(y.equals(T.nil)) {
      T.root = z;
    }
    else if (z.key < y.key) {
      y.left = z;
    }
    else {
      y.right = z;
    }
    z.left = T.nil;
    z.right = T.nil;
    z.color = false;
    RBInsertFixup(T, z);
  }

  public void RBInsertFixup(RBTree T, RBNode z) {
    while(!z.p.color) {
      if(z.p.equals(z.p.p.left)) {
        RBNode y = z.p.p.right;
        if(!y.color) {
          z.p.color = true;
          y.color = true;
          z.p.p.color = false;
          z = z.p.p;
        }
        else {
          if(z.equals(z.p.right)) {
            z = z.p;
            LeftRotate(T, z);
          }
          z.p.color = true;
          z.p.p.color = false;
          RightRotate(T, z.p.p);
        }
      }
      else {
        RBNode y = z.p.p.left;
        if(!y.color) {
          z.p.color = true;
          y.color = true;
          z.p.p.color = false;
          z = z.p.p;
        }
        else {
          if(z.equals(z.p.left)) {
            z = z.p;
            RightRotate(T, z);
          }
          z.p.color = true;
          z.p.p.color = false;
          LeftRotate(T, z.p.p);
        }
      }
    }
    T.root.color = true;
  }

  public void RBTransplant(RBTree T, RBNode u, RBNode v) {
    if(u.p.equals(T.nil)) {
      T.root = v;
    }
    else if (u.equals(u.p.left)) {
      u.p.left = v;
    }
    else {
      u.p.right = v;
    }
    v.p = u.p;
  }

  public void InorderTreeWalk(RBTree T, RBNode x) {
    if(x != T.nil) {
      InorderTreeWalk(T, x.left);
      System.out.println(x.key);
      InorderTreeWalk(T, x.right);
    }
  }

  public RBNode TreeSearch(RBTree T, RBNode x, int k) {
    if(x.equals(T.nil) || x.key.equals(k)) {
      return x;
    }
    if (k < x.key) {
      return TreeSearch(T, x.left, k);
    }
    return TreeSearch(T, x.right, k);
  }

  public RBNode IterativeTreeSearch(RBTree T, RBNode x, int k) {
    while(x != T.nil && k != x.key) {
      if (k < x.key) {
        x = x.left;
      } else {
        x = x.right;
      }
    }
    return x;
  }

  public RBNode TreeMinimum(RBTree T, RBNode x) {
    while(x.left != T.nil) {
      x = x.left;
    }
    return x;
  }

  public RBNode TreeMaximum(RBTree T, RBNode x) {
    while(x.right != T.nil) {
      x = x.right;
    }
    return x;
  }

  public RBNode TreeSuccessor(RBTree T, RBNode x) {
    if(x.right != T.nil) {
      return TreeMinimum(T, x.right);
    }
    RBNode y = x.p;
    while(y != T.nil && x.equals(y.right)) {
      x = y;
      y = y.p;
    }
    return y;
  }

  public RBNode TreePredecessor(RBTree T, RBNode x) {
    if(x.left != T.nil) {
      return TreeMaximum(T, x.left);
    }
    RBNode y = x.p;
    while(y != T.nil && x.equals(y.left)) {
      x = y;
      y = y.p;
    }
    return y;
  }

  public void RBDelete(RBTree T, int num) {
    BSTTreeDelete(T, TreeSearch(T, T.root, num));
  }

  public void BSTTreeDelete(RBTree T, RBNode z) {
    if(z.left == T.nil) {
      RBTransplant(T, z, z.right);
    }
    else if(z.right == T.nil) {
      RBTransplant(T, z, z.left);
    }
    else {
      RBNode y =  TreeMinimum(T, z.right);
      if(y != z.right) {
        RBTransplant(T, y, y.right);
        y.right = z.right;
        y.right.p = y;
      }
      RBTransplant(T, z, y);
      y.left = z.left;
      y.left.p = y;
    }
  }

  public void RBTreeDelete(RBTree T, RBNode z) {
    RBNode y = z;
    boolean yOriginalColor = y.color;
    RBNode x;
    if(z.left.equals(T.nil)) {
      x = z.right;
      RBTransplant(T, z, z.right);
    } else if (z.right.equals(T.nil)) {
      x = z.left;
      RBTransplant(T, z, z.left);
    } else {
      y = TreeMinimum(T, z.right);
      yOriginalColor = y.color;
      x = y.right;
      if(y != z.right) {
        RBTransplant(T, y, y.right);
        y.right = z.right;
        y.right.p = y;
      } else {
        x.p = y;
      }
      RBTransplant(T, z, y);
      y.left = z.left;
      y.left.p = y;
      y.color = z.color;
      if(yOriginalColor) {
        RBDeleteFixup(T, x);
      }
    }
  }

  public void RBDeleteFixup(RBTree T, RBNode x) {
    while (x != T.root && x.color) {
      if(x.equals(x.p.left)) {
        RBNode w = x.p.right;
        if(!w.color) {
          w.color = true;
          x.p.color = false;
          LeftRotate(T, x.p);
          w = x.p.right;
        }
        if(w.left.color && w.right.color) {
          w.color = false;
          x = x.p;
        }
        else {
          if(w.right.color) {
            w.left.color = true;
            w.color = false;
            RightRotate(T, w);
            w = x.p.right;
          }
          w.color = x.p.color;
          x.p.color = true;
          x.right.color = true;
          LeftRotate(T, x.p);
          x = T.root;
        }
      } else {
        RBNode w = x.p.left;
        if(!w.color) {
          w.color = true;
          x.p.color = false;
          RightRotate(T, x.p);
          w = x.p.left;
        }
        if(w.right.color && w.left.color) {
          w.color = false;
          x = x.p;
        }
        else {
          if(w.left.color) {
            w.right.color = true;
            w.color = false;
            LeftRotate(T, w);
            w = x.p.left;
          }
          w.color = x.p.color;
          x.p.color = true;
          w.left.color = true;
          RightRotate(T, x.p);
          x = T.root;
        }
      }
    }
    x.color = true;
  }

  public String displayTree() {
    return this.root.display(this.root, 0);
  }

  public int getHeight(RBNode x) {
    if(x.equals(this.nil)) {
      return 0;
    }
    return 1 + Math.max(getHeight(x.right), getHeight(x.left));
  }
}