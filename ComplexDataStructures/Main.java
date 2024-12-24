import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

  static String hashInput = "src/input/input.txt";
  static String rbInput = "src/input/nums.txt";

  public static void main(String[] args) throws IOException {
    runHash();
    runBinomialHeap();
    runSkipList();
    runRBTree();
  }


  public static void runBinomialHeap() throws IOException {

    BufferedWriter fileWriter =
            new BufferedWriter(new FileWriter("src/output/binomialheap_output/Heapoutput.txt"));


    BiHeap H1 = BiHeap.makeBinomialHeap();
    BiHeap.binomialHeapInsert(H1, new BHNode(3));
    BiHeap.binomialHeapInsert(H1, new BHNode(4));
    BiHeap.binomialHeapInsert(H1, new BHNode(7));
    BiHeap.binomialHeapInsert(H1, new BHNode(1));
    BiHeap.binomialHeapInsert(H1, new BHNode(8));
    System.out.println(BiHeap.binomialHeapDisplay(H1));
    BiHeap.binomialHeapExtractMin(H1);
    System.out.println(BiHeap.binomialHeapDisplay(H1));
    BiHeap.binomialHeapDecreaseKey(H1, BiHeap.binomialHeapFind(H1, H1.head, 7), 2);
    System.out.println(BiHeap.binomialHeapDisplay(H1));

    BiHeap H2 = BiHeap.makeBinomialHeap();
    BiHeap.binomialHeapInsert(H2, new BHNode(10));
    BiHeap.binomialHeapInsert(H2, new BHNode(12));
    BiHeap.binomialHeapInsert(H2, new BHNode(13));
    System.out.println(BiHeap.binomialHeapDisplay(H2));
    BiHeap.binomialHeapDelete(H2, BiHeap.binomialHeapFind(H2, H2.head, 10));
    System.out.println(BiHeap.binomialHeapDisplay(H2));

    BiHeap H3 = BiHeap.binomialHeapUnion(H1, H2);
    System.out.println(BiHeap.binomialHeapDisplay(H3));

    fileWriter.close();

  }

  public static void runHash() throws IOException {
    TextHash hash30 = new TextHash(30);
    TextHash hash300 = new TextHash(300);
    TextHash hash1000 = new TextHash(1000);

    hash30.readFromFile(hashInput);
    hash300.readFromFile(hashInput);
    hash1000.readFromFile(hashInput);

    hash30.listAllKeys();
    hash30.makeHistogram();

    hash300.listAllKeys();
    hash300.makeHistogram();

    hash1000.listAllKeys();
    hash1000.makeHistogram();
  }





  public static void runSkipList() throws IOException {
    BufferedWriter fileWriter =
            new BufferedWriter(new FileWriter("src/output/skiplist_output/output.txt"));

    SNode found;

    SkipList sl = new SkipList(10);
    fileWriter.write("Made a new skip list of height ten!\n" + sl.displayList() + System.lineSeparator());

    sl.insert(10, fileWriter);
    fileWriter.write("Inserted 10!\n" + sl.displayList() + System.lineSeparator());

    sl.insert(10, fileWriter);
    fileWriter.write("Inserted 10!\n" + sl.displayList() + System.lineSeparator());

    sl.insert(15, fileWriter);
    fileWriter.write("Inserted 15!\n" + sl.displayList() + System.lineSeparator());

    sl.insert(8, fileWriter);
    fileWriter.write("Inserted 8!\n" + sl.displayList() + System.lineSeparator());

    sl.insert(2, fileWriter);
    fileWriter.write("Inserted 2!\n" + sl.displayList() + System.lineSeparator());

    sl.insert(5, fileWriter);
    fileWriter.write("Inserted 5!\n" + sl.displayList() + System.lineSeparator());

    sl.delete(8);
    fileWriter.write("Deted 8! \n" + sl.displayList() + System.lineSeparator());

      found = sl.find(80);
    if(found == null) {
      fileWriter.write("Looked up 80 and found: " + found  + System.lineSeparator() + System.lineSeparator());
    } else {
      fileWriter.write("Looked up 80 and found: " + found + " containing value "
              + found.val + System.lineSeparator() + System.lineSeparator());
    }

    found = sl.find(5);
    if(found == null) {
      fileWriter.write("Looked up 5 and found: " + found  + System.lineSeparator() + System.lineSeparator());
    } else {
      fileWriter.write("Looked up 5 and found: " + found + " containing value "
              + found.val + System.lineSeparator() + System.lineSeparator());
    }
    fileWriter.close();
  }












  public static void runRBTree() throws IOException {
    RBTree t = new RBTree();
    Scanner sc = new Scanner(System.in);
    Appendable app = System.out;
    while(true) {

      app.append("Please enter a command!\nSort | Search | Min | Max | Insert | Delete | q\n");
      app.append("Command: ");
      boolean wroteCommand = false;
      String userInstruction = sc.next();

      if(userInstruction.equalsIgnoreCase("Sort")) {
        wroteCommand = true;
        Scanner scan = new Scanner(new File(rbInput));
        int count = 0;
        while(scan.hasNext()) {
          t.RBInsert(t, new RBNode(scan.nextInt()));
          count++;
        }
        app.append("Inserted ").append(String.valueOf(count)).append(" numbers").append(System.lineSeparator());
        app.append("Height of tree: ").append(String.valueOf(t.getHeight(t.root))).append(System.lineSeparator());
        app.append(String.valueOf(t.displayTree())).append(System.lineSeparator());

      }

      if(userInstruction.equalsIgnoreCase("Search")) {
        wroteCommand = true;
        app.append("Please enter the number to search for: ");
          Integer num = null;
          while (num == null) {
            try {
              num = sc.nextInt();
            } catch (Exception e) {
              sc.nextLine();
              app.append("Invalid input!\n");
            }
          }
          app.append("Searched ").append(String.valueOf(num))
                  .append(" and found: ")
                  .append(String.valueOf(t.TreeSearch(t, t.root, num).key)).append(System.lineSeparator());
      }

      if(userInstruction.equalsIgnoreCase("Insert")) {
        wroteCommand = true;
        app.append("Please enter the number to insert: ");
        Integer num = null;
        while (num == null) {
          try {
            num = sc.nextInt();
          } catch (Exception e) {
            sc.nextLine();
            app.append("Invalid input!\n");
          }
        }
        t.RBInsert(t, new RBNode(num));
        app.append("Inserted ").append(String.valueOf(num)).append(System.lineSeparator());
        app.append("Height of tree: ").append(String.valueOf(t.getHeight(t.root))).append(System.lineSeparator());
        app.append(String.valueOf(t.displayTree())).append(System.lineSeparator());
      }

      if(userInstruction.equalsIgnoreCase("Delete")) {
        wroteCommand = true;
        app.append("Please enter the number to delete: ");
        Integer num = null;
        while (num == null) {
          try {
            num = sc.nextInt();
          } catch (Exception e) {
            sc.nextLine();
            app.append("Invalid input!\n");
          }
        }
        t.RBDelete(t, num);
        app.append("Deleted ").append(String.valueOf(num)).append(System.lineSeparator());
        app.append("Height of tree: ").append(String.valueOf(t.getHeight(t.root))).append(System.lineSeparator());
        app.append(String.valueOf(t.displayTree())).append(System.lineSeparator());
      }

      if(userInstruction.equalsIgnoreCase("Min")) {
        wroteCommand = true;
        app.append("The minimum is: ").append(String.valueOf(t.TreeMinimum(t, t.root).key)).append(System.lineSeparator());
      }

      if(userInstruction.equalsIgnoreCase("root")) {
        wroteCommand = true;
        app.append(String.valueOf(t.root.key)).append(System.lineSeparator());
      }

      if(userInstruction.equalsIgnoreCase("Max")) {
        wroteCommand = true;
        app.append("The maximum is: ").append(String.valueOf(t.TreeMaximum(t, t.root).key)).append(System.lineSeparator());
      }

      if (userInstruction.equalsIgnoreCase("q") || userInstruction.equalsIgnoreCase("quit")) {
        break;
      }

      if(!wroteCommand) {
        app.append("Invalid command! ");
      }
    }
  }
}
