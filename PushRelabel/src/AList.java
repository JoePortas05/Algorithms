import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

// The only reason this class exists is because in Discharge I didn't want to try getting
// a list index out of bounds and catch returning null, so I made a wrapper class to do that for
// me and be pretty :]
public class AList<T> implements Iterable<T>{
  private final List<T> lst;

  public AList() {
    lst = new ArrayList<>();
  }

  public void add(T elem) {
    lst.add(elem);
  }

  public T getFirst() {
    return lst.getFirst();
  }

  public T get(int index) {
    try {
      return lst.get(index);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  public boolean contains(T e) {
    return lst.contains(e);
  }

  public int size() {
    return lst.size();
  }

  public Iterator<T> iterator() {
    return lst.iterator();
  }

  public T getLast() {
    return lst.getLast();
  }

  public T find(T item) {
    for(T elem : lst) {
      if(item.equals(elem)) {
        return elem;
      }
    }
    return null;
  }

  public void sort(Comparator<? super T> c) {
    lst.sort(c);
  }
}
