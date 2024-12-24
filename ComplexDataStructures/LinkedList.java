import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<T> {
  T item;
  Node<T> next;
  Node<T> prev;

  public Node(T item) {
    this.item = item;
    this.next = null;
    this.prev = null;
  }
}

public class LinkedList<T> implements Iterable<T> {
  Node<T> head;
  int size;

  public LinkedList() {
    this.head = null;
    this.size = 0;
  }

  public void add(T item) {
    this.size += 1;
    if(this.head == null) {
      this.head = new Node<>(item);
      return;
    }
    Node<T> cur = this.head;
    while(cur.next != null) {
      cur = cur.next;
    }
   Node<T> newNode = new Node<>(item);
    cur.next = newNode;
    newNode.prev = cur;
  }

  public void remove(T item) {
    if(this.size == 0) {
      return;
    }

    Node<T> cur = this.head;
    Node<T> beforeCur = null;

    while(cur != null) {

      if(cur.item.equals(item) && beforeCur == null) {
        if(this.size == 1) {
          this.head = null;
        } else {
          this.head = this.head.next;
          this.head.prev = null;
        }
        this.size -= 1;
        return;
      }

      if(cur.item.equals(item)) {
        beforeCur.next = cur.next;
        if(cur.next != null) {
          cur.next.prev = beforeCur;
        }
        this.size -= 1;
        break;
      }

      if(beforeCur == null) {
        beforeCur = this.head;
      } else {
        beforeCur = beforeCur.next;
      }
      cur = cur.next;
    }
  }

  public int size() {
    return this.size;
  }

  @Override
  public Iterator<T> iterator() {
    return new LinkedListIterator();
  }

  private class LinkedListIterator implements Iterator<T> {

    private Node<T> cur = head;

    @Override
    public boolean hasNext() {
      return cur != null;
    }

    @Override
    public T next() {
      if(this.hasNext()) {
        T item = this.cur.item;
        this.cur = this.cur.next;
        return item;
      }
      throw new NoSuchElementException();
    }
  }
}
