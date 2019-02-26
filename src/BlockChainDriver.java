import java.io.PrintWriter;

public class BlockChainDriver {
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    
    // Tests:
    Hash hash = new Hash (new byte [] {0, 56, 45});
    Block b1 = new Block (1, 13, hash);
    
    pen.println(hash.toString());
    pen.println(b1.toString());
  }
}
