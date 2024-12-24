import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class TextPair {
  public String k;
  public int v;

  public TextPair(String k, int v) {
    this.k = k;
    this.v = v;
  }
}

class HashList {
  private final LinkedList<TextPair> list;

  public HashList() {
    this.list = new LinkedList<>();
  }

  public int length() {
    return this.list.size();
  }

  public TextPair getTextPair(String k) {
    for(TextPair p : this.list) {
      if(p.k.equals(k)) {
        return p;
      }
    }
    return null;
  }

  public void insert(String k, int v) {
    TextPair p = this.getTextPair(k);
    this.list.remove(p);
    this.list.add(new TextPair(k, v));
  }

  public void increase(String k) {
    TextPair p = this.getTextPair(k);
    if(p == null) {
      return;
    }
    int newVal = p.v + 1;
    this.list.remove(p);
    this.list.add(new TextPair(k, newVal));
  }

  public void delete(String k) {
    TextPair p = this.getTextPair(k);
    if(p == null) {
      return;
    }
    this.list.remove(p);
  }

  public int find(String k) {
    TextPair p = this.getTextPair(k);
    if(p == null) {
      return 0;
    }
    return p.v;
  }

  public int countAllWords() {
    int count = 0;
    for(TextPair p : this.list) {
      count += p.v;
    }
    return count;
  }

  public void listAllKeys(BufferedWriter fileWriter) throws IOException {
    for(TextPair p : this.list) {
      String builder = p.k +
              " " +
              p.v +
              "\n";
      fileWriter.write(builder);
    }
  }
}

public class TextHash {

  private final int m;
  private final HashList[] table;

  public TextHash(int m) {
    this.m = m;
    this.table = new HashList[m];
    for(int i = 0; i < m; i++) {
      this.table[i] = new HashList();
    }
  }

  public int h(String k) {
    k = k.toLowerCase();
    int code = 31;
    for(char c : k.toCharArray()) {
      code = code * 41 + c;
    }
    return Math.abs(code % m);
  }

  public void insert(String k, int v) {
    k = k.toLowerCase();
    this.table[this.h(k)].insert(k, v);
  }

  public void delete(String k) {
    k = k.toLowerCase();
    int index = this.h(k);
    this.table[index].delete(k);
  }

  public void increase(String k) {
    k = k.toLowerCase();
    int index = this.h(k);
    this.table[index].increase(k);
  }

  public int findKey(String k) {
    k = k.toLowerCase();
    int index = this.h(k);
    return this.table[index].find(k);
  }

  public int countAllWords() {
    int count = 0;
    for(int i = 0; i < m; i++) {
      count += this.table[i].countAllWords();
    }
    return count;
  }

  public int countAllKeys() {
    int count = 0;
    for(int i = 0; i < m; i++) {
      count += this.table[i].length();
    }
    return count;
  }

  public void listAllKeys() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter("src/output/hash_output/" + m + "AllKeys.txt"));
    fileWriter.write(this.countAllWords() + " Total Words\n");
    fileWriter.write(this.countAllKeys() + " Unique Keys\n\n");
    for(int i = 0; i < m; i++) {
      this.table[i].listAllKeys(fileWriter);
    }
    fileWriter.close();
  }

  public void makeHistogram() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter("src/output/hash_output/" + m + "Histogram.txt"));
    List<Integer> lengths = new ArrayList<>();
    for (HashList b : this.table) {
      if (!lengths.contains(b.length())) {
        lengths.add(b.length());
      }
    }
    if (lengths.isEmpty()) {
      fileWriter.write("Cannot display histogram");
      fileWriter.close();
      return;
    }
    lengths.sort(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        if(Objects.equals(o1, o2)) {
          return 0;
        }
        if(o1 > o2) {
          return 1;
        }
        return -1;
      }
    });
    StringBuilder message = new StringBuilder();
    int numOfStars = 0;
    for (int length : lengths) {
      for(HashList b : this.table) {
        if(b.length() == length) {
          numOfStars++;
        }
      }
      message.append("Length ").append(length).append(" (").append(numOfStars).append(")").append(": ");
      message.append("*".repeat(numOfStars));
      message.append(System.lineSeparator());
    }
    int sum = 0;
    for(int i : lengths) {
      sum += i;
    }
    int avg = sum / lengths.size();
    int varSum = 0;
    for(int i : lengths) {
      varSum += (i- avg) * (i-avg);
    }
    int variance = varSum / lengths.size();
    message.append("Number of lists in a given length.").append(System.lineSeparator());
    message.append("Variance: ").append(variance).append(System.lineSeparator());
    fileWriter.write(message.toString());
    fileWriter.close();
  }

  public void readFromFile(String filename) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(filename));
    while(sc.hasNext()) {
      String line = sc.nextLine();
      String[] words = line.split("[^\\w']+|_+");
      for(String k : words) {
        if(this.findKey(k) != 0) {
          this.increase(k);
        }
        else {
          this.insert(k, 1);
        }
      }
    }
    sc.close();
  }
}
