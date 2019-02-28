import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

public class BlockChain {

  // Simple 'Node' class
  // Has fields for a value (which will be a Block in this case) and a next Node
  public class Node<T> {
    T val;
    Node<T> next;

    // Construct a new Node with a value for it and a pointer to the next Node
    public Node(T val, Node<T> next) {
      this.val = val;
      this.next = next;
    }
  }

  // Fields

  Node<Block> first;
  Node<Block> last;

  // Constructor

  // Construct a new BlockChain with a first Block and a last initialized as first
  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first = new Node<Block>(new Block(0, initial, null), null);
    this.last = this.first;
  }

  // Methods

  // Purpose: Mine a new Block that could be next in the BlockChain
  // Pre: amount must represent a valid transaction between "Alice and Bob" (i.e. they cannot
  //      drop below $0
  // Post: Throw an IllegalArgumentException if the Hash of the mined Block is not equal to the
  //       previousHash of the previous Block
  //       Throw an IllegalArgumentException if the Hash of the mined Block is not valid (does
  //       not start with 000)
  //       Return a Block which should be valid to append (see below) to the current BlockChain
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

  // Purpose: Get the number of Blocks (wrapped in Nodes) in the BlockChain
  public int getSize() {
    return this.last.val.getNum() + 1;
  }

  // Purpose: Add a new Block (wrapped in a Node) to the end of the BlockChain
  // Pre: blk must be a valid Block (in terms of its Hash and ability to be attached to the
  //      BlockChain)
  // Post: The last field of the current BlockChain is now blk (wrapped in a Node)
  //       The next field of the previous Node points to blk's Node
  //       Throw an IllegalArgumentException instead if the amount of blk would result in a
  //       negative total for Alice or Bob.
  public void append(Block blk) throws IllegalArgumentException {
    // Initialize a new Node with the given Block, as well as values to keep track of Alice
    // and Bob's totals throughout the transactions represented by the existing BlockChain
    Node<Block> newNode = new Node<Block>(blk, null);
    int aliceAmt = this.first.val.getAmount();
    int bobAmt = 0;
    Node<Block> thisNode = this.first.next;
    // Nonfunctional iteration tactic; left for posterity/possible future review
    // for (int i = 0; i < this.getSize(); i++) {
    // Iterate through the existing BlockChain to find Alice and Bob's totals before adding blk
    // to the end of the BlockChain
    while (thisNode != null) {
      aliceAmt += thisNode.val.getAmount();
      bobAmt -= thisNode.val.getAmount();
      thisNode = thisNode.next;
    } // for
    // Throw an IllegalArgumentException if the amount field of blk would result in invalid
    // money totals for either Alice or Bob
    if ((aliceAmt + newNode.val.getAmount() < 0) || (bobAmt - newNode.val.getAmount() < 0)) {
      throw new IllegalArgumentException("The parameter Block has an invalid amount field!");
    } else {
      // Assuming all conditions are met, append blk's Node to the end of the current BlockChain
      this.last.next = newNode;
      this.last = this.last.next;
    }
  }

  // Purpose: Remove the last Block's Node from the BlockChain
  // Post: Return true if the last Block's Node was successfully removed from the BlockChain
  //       Return false if there weren't at least two Nodes in the BlockChain
  public boolean removeLast() {
    if (this.getSize() > 1) {
      // Iterate through the existing BlockChain to get the second to last Node
      Node<Block> secondToLast = this.first;
      while (secondToLast.next != this.last) {
        secondToLast = secondToLast.next;
      } // while
      // Change the second to last Node's fields to remove the last Node
      this.last = secondToLast;
      secondToLast.next = null;
      return true;
    } // if
    else {
      return false;
    }
  }

  // Purpose: Return the Hash of the last Block of the BlockChain
  public Hash getHash() {
    return this.last.val.getHash();
  }

  // Purpose: Find whether the BlockChain is valid (in terms of the Hashes matching up between
  //          adjacent Blocks
  // Post: Return true if all the Hashes match Block to Block, return false if not.
  public boolean isValidBlockChain() {
    if (this.getSize() > 1) {
      // iterate through all the Nodes, checking the Hashes of the corresponding Blocks
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

  // Purpose: Print the current balances of Alice and Bob
  // Post: Alice and Bob's amounts must be >= 0
  public void printBalances() {
    PrintWriter pen = new PrintWriter(System.out, true);
    int aliceAmt = this.first.val.getAmount();
    int bobAmt = 0;
    Node<Block> thisNode = this.first.next;
    // Nonfunctional iteration strategy; left in for posterity/future analysis
    // for (int i = 0; i < this.getSize(); i++) {
    // Iterate through the BlockChain, using the amount fields to keep track of Alice and Bob's
    // totals
    while (thisNode != null) {
      aliceAmt += thisNode.val.getAmount();
      bobAmt -= thisNode.val.getAmount();
      thisNode = thisNode.next;
    }
    pen.println("Alice: " + aliceAmt + ", Bob: " + bobAmt);
  }

  // Purpose: Use the Block class's toString on all of the Blocks in the BlockChain
  // Post: Return a String that is an organized collection of all of the toStrings of the Blocks
  //       in the BlockChain
  public String toString() {
    Node<Block> thisNode = this.first;
    String ret = "";
    // Iterate through every Block in the BlockChain, adding to the String each time
    for (int i = 0; i < this.getSize(); i++) {
      ret += "";
      ret += thisNode.val.toString();
      ret += "\n";
      thisNode = thisNode.next;
    }
    return ret;
  }
}
