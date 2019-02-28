import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

public class BlockChain {
  
  public class Node<T> {
    T val;
    Node<T> next;
    
    public Node (T val, Node<T> next) {
      this.val = val;
      this.next = next;
    }
  }
  
  // Fields
  
  Node<Block> first;
  Node<Block> last;
  // note: first block describes how much money Alice has

  // Constructor
  
  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first = new Node<Block> (new Block(0, initial, null), null); 
    this.last = this.first;
  }

  // Methods
  
  public Block mine(int amount) throws NoSuchAlgorithmException {
    Block blk = new Block(this.last.val.getNum() + 1, amount, this.last.val.getHash());
    return blk;
    // check to see if amount is valid
  }

  public int getSize() {
    return this.last.val.getNum();
  }
  
  public void append(Block blk) throws IllegalArgumentException {
    Node<Block> newNode = new Node<Block>(blk, null);
    this.last.next = newNode;
    this.last = this.last.next;
    // throw illegalargumentexception if block is invalid
  }
  
  public boolean removeLast() {
    if (this.getSize() > 1) {
      this.last = null;
      return true;
    }
    else {
      return false;
    }
    // we need to make sure this.last points to the new last block,
    // will have to iterate through from beginning (yike)
  }
  
  public Hash getHash() {
    // return hash of last block
  }
  
  public boolean isValidBlockChain() {
    // checks to see that all hashes match up
    // anything else?
    return true;
  }
  
  public void printBalances() {
    PrintWriter pen = new PrintWriter(System.out, true);
    pen.println("Alice: " + aliceAmt + ", Bob: " + bobAmt);
    // go through every block to find out
  }
  
  public String toString() {
    Node<Block> thisNode = this.first;
    String ret = "";
    for (int i = 0; i < this.getSize(); i++) {
      ret += thisNode.val.toString();
      ret += "\n";
      thisNode = thisNode.next;
    }
    return ret;
  }
}
