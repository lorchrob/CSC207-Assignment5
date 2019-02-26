import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
  int num;
  int amount;
  Hash previousHash;
  long nonce;
  Hash currentHash;

  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.previousHash = prevHash;

    long nonce = -1;
    byte[] newHashData;
    MessageDigest md = MessageDigest.getInstance("sha-256");
    do {
      nonce++;
      
      md.update(ByteBuffer.allocate(4).putInt(this.num).array());
      md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
      md.update(previousHash.data); // DO THIS ONLY IF THERE IS A PREVIOUS HASH
      md.update(ByteBuffer.allocate(8).putLong(nonce).array());
      newHashData = md.digest();
    } while (newHashData[0] != 0 || newHashData[1] != 0 || newHashData[2] != 0);
    
    this.nonce = nonce;
    this.currentHash = new Hash(newHashData);
  }
  
  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.previousHash = prevHash;
    
    MessageDigest md = MessageDigest.getInstance("sha-256");
    
    md.update(ByteBuffer.allocate(4).putInt(this.num).array());
    md.update(ByteBuffer.allocate(4).putInt(this.amount).array());
    md.update(previousHash.data); // DO THIS ONLY IF THERE IS A PREVIOUS HASH
    md.update(ByteBuffer.allocate(8).putLong(this.nonce).array());
    byte[] newHashData = md.digest();
    
    this.currentHash = new Hash(newHashData);
  } 

  public int getNum() {
    return this.num;
  }

  public int getAmount() {
    return this.amount;
  }

  public long getNonce() {
    return this.nonce;
  }

  public Hash getPrevHash() {
    return this.previousHash;
  }

  public Hash getHash() {
    return this.currentHash;
  }

  public String toString() {
    return "Block " + this.num + " (Amount: " + this.amount + ", Nonce: " + this.nonce
        + ", prevHash: " + this.previousHash.toString() + ", hash: " + this.currentHash.toString()
        + ")";
  }
}
