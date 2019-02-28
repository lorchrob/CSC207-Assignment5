import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

public class BlockChain {

  // Simple 'Node' class
  
  public class Node<T> {
    T val;
    Node<T> next;

    public Node(T val, Node<T> next) {
      this.val = val;
      this.next = next;
    }
  }

  // Fields

  Node<Block> first;
  Node<Block> last;

  // Constructor

  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first = new Node<Block>(new Block(0, initial, null), null);
    this.last = this.first;
  }

  // Methods

  public Block mine(int amount) throws NoSuchAlgorithmException, IllegalArgumentException {
    Block blk = new Block(this.last.val.getNum() + 1, amount, this.last.val.getHash());
    if (this.last.val.getHash() != blk.getPrevHash()) {
      throw new IllegalArgumentException(
          "The hash of the mined block doesn't match the previous hash!");
    } else if (!blk.getHash().isValid()) {
      throw new IllegalArgumentException("The hash of the mined block is invalid!");
    } else {
      return blk;
    }
  }

  public int getSize() {
    return this.last.val.getNum() + 1;
  }

  public void append(Block blk) throws IllegalArgumentException {
    Node<Block> newNode = new Node<Block>(blk, null);
    int aliceAmt = this.first.val.getAmount();
    int bobAmt = 0;
    Node<Block> thisNode = this.first.next;
    //for (int i = 0; i < this.getSize(); i++) {
      while (thisNode != null) {
      aliceAmt += thisNode.val.getAmount();
      bobAmt -= thisNode.val.getAmount();
      thisNode = thisNode.next;
    } // for
    if ((aliceAmt + newNode.val.getAmount() < 0) || (bobAmt - newNode.val.getAmount() < 0)) {
      throw new IllegalArgumentException("The parameter Block has an invalid amount field!");
    } else {
      this.last.next = newNode;
      this.last = this.last.next;
    }
  }

  public boolean removeLast() {
    if (this.getSize() > 1) {
      Node<Block> secondToLast = this.first;
      while (secondToLast.next != this.last) {
        secondToLast = secondToLast.next;
      }
      this.last = secondToLast;
      secondToLast.next = null;
      return true;
    } else {
      return false;
    }
  }

  public Hash getHash() {
    return this.last.val.getHash();
  }

  public boolean isValidBlockChain() {
    if (this.getSize() > 1) {
      Node<Block> thisNode = this.first;
      for (int i = 0; i < (this.getSize() - 1); i++) {
        if (thisNode.val.getHash() != thisNode.next.val.getPrevHash()) {
          return false;
        } // if
        thisNode = thisNode.next;
      } // for
      return true;
    } else {
      return true;
    }
  }

  public void printBalances() {
    PrintWriter pen = new PrintWriter(System.out, true);
    int aliceAmt = this.first.val.getAmount();
    int bobAmt = 0;
    Node<Block> thisNode = this.first.next;
    //for (int i = 0; i < this.getSize(); i++) {
    while (thisNode != null) {
      aliceAmt += thisNode.val.getAmount();
      bobAmt -= thisNode.val.getAmount();
      thisNode = thisNode.next;
    }
    pen.println("Alice: " + aliceAmt + ", Bob: " + bobAmt);
  }

  public String toString() {
    Node<Block> thisNode = this.first;
    String ret = "";
    for (int i = 0; i < this.getSize(); i++) {
      ret += "";
      ret += thisNode.val.toString();
      ret += "\n";
      thisNode = thisNode.next;
    }
    return ret;
  }
}
