import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
  
  // Fields
  
  int num;
  int amount;
  Hash previousHash;
  long nonce;
  Hash currentHash;

  // Constructors
  
  /*
   * Purpose: 
   *   * to make a new block with the given fields
   *   * note: no nonce is given, so a valid nonce must be mined */
  // Pre:
  //   * num should correspond to the number of block in the blockchain, zero-based indexing
  //   * amount should correspond to a transaction amount; neither person should have less than 0 dollars
  //       - negative is Alice -> Bob, positive is Bob -> Alice
  //   * prevHash should refer to the hash of the previous block
  // Post:
  //   * makes a new block with a valid hash (starts with 3 zeros) and the corresponding fields
  
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.previousHash = prevHash;

    long nonce = -1; // this will be incremented to 0 for the first iteration of the loop 
                     // to get a valid nonce
    byte[] newHashData;
    MessageDigest md = MessageDigest.getInstance("sha-256");
    do {
      nonce++;

      // update the message digest object with the information from the block
      // and from the current nonce value
      
      md.update(ByteBuffer.allocate(4).putInt(this.num).array());
      md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
      if (prevHash != null) {
        md.update(previousHash.data);
      }
      md.update(ByteBuffer.allocate(8).putLong(nonce).array());
      newHashData = md.digest();
    } while (newHashData[0] != 0 || newHashData[1] != 0 || newHashData[2] != 0); // try until we get
                                                                                 // a valid nonce

    this.nonce = nonce;
    this.currentHash = new Hash(newHashData);
  }

  // method to make a block, given that you already have a valid nonce
  //   * same as previous constructor, but you don't have try search for a valid nonce
  // Pre:
  //   * num should correspond to the number of block in the blockchain, zero-based indexing
  //   * amount should correspond to a transaction amount; neither person should have less than 0 dollars
  //       - negative is Alice -> Bob, positive is Bob -> Alice
  //   * prevHash should refer to the hash of the previous block
  //   * the given nonce should be valid (should lead to a valid hash, as described below)
  // Post:
  //   * makes a new block with a valid hash (starts with 3 zeros) and the corresponding fields
  
  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.previousHash = prevHash;
    this.nonce = nonce;

    MessageDigest md = MessageDigest.getInstance("sha-256");
    
    md.update(ByteBuffer.allocate(4).putInt(this.num).array());
    md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
    if (prevHash != null) {
      md.update(previousHash.data);
    }
    md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
    byte[] newHashData = md.digest();

    this.currentHash = new Hash(newHashData);
  }

  // Methods
  
  // method to return the number of the block
  
  public int getNum() {
    return this.num;
  }

  // method to return block's amount
  
  public int getAmount() {
    return this.amount;
  }

  // method to return block's nonce
  
  public long getNonce() {
    return this.nonce;
  }

  // method to return block's previous hash
  // * note: could be null
  
  public Hash getPrevHash() {
    return this.previousHash;
  }

  // method to return block's hash
  
  public Hash getHash() {
    return this.currentHash;
  }

  /*
   * Method to represent block as a string
   *   * note: if the previous hash is null, it is demonstrated as such
   */
  
  public String toString() {
    if (this.previousHash != null) {
    return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
        + ", prevHash: " + this.previousHash.toString() + ", hash: " + this.currentHash.toString()
        + ")";
    } else {
      return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
          + ", prevHash: null, hash: " + this.currentHash.toString()
          + ")";
    }
  }
}
