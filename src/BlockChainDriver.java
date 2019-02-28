import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class BlockChainDriver {
  
  /*
   * Main method:
   *   * Method to utilized 'Block', 'BlockChain', and 'Hash'
   *   * Start with a blockchain with a single block, and use the given commands
   *     to build your blockchain
   *   * The amounts of the blocks in the blockchain correspond to transactions, with
   *     positive being from Bob to Alice and negative being from Alice to Bob
   * Preconditions:
   *   * 1 Command-line argument, which corresponds to Alice's initial balance
   *   * Transactions cannot bring either person below 0 dollars
   *   * invalid nonces/amounts cannot be added to the blockchain
   * Postconditions:
   *   * blockchain is crafted according to the user's input
   */
  
  public static void main(String[] args) throws NoSuchAlgorithmException {
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner scan = new Scanner(System.in);

    BlockChain blockChain = new BlockChain(Integer.parseInt(args[0])); 
    Block storage = null;

    pen.print(blockChain.toString());
    pen.println("Command?");
    String input = scan.next();

   // Execute commands while input is not "quit"

    while (!input.equals("quit")) {
      if (input.equals("mine")) {
        pen.println("Amount transferred? ");
        int amt = Integer.parseInt(scan.next());
        storage = blockChain.mine(amt);
        pen.println("Amount = " + amt + ", nonce = " + storage.getNonce());
        pen.println("");
      } else if (input.contentEquals("append")) {
        pen.println("Amount transferred? ");
        int amt = Integer.parseInt(scan.next());
        pen.println("Nonce? ");
        long nnc = Long.parseLong(scan.next());
        if (storage != null) {
          if (storage.amount != amt) {
            pen.println("That amount does not correspond to the mined Block!");
          } else if (storage.nonce != nnc) {
            pen.println("That nonce does not correspond to the mined Block!");
          } else {
            blockChain.append(storage);
          }
        } else {
          pen.println("No Block has been mined!");
        }
        pen.println("");
      } else if (input.contentEquals("remove")) {
        blockChain.removeLast();
        pen.println("");
      } else if (input.contentEquals("check")) {
        if (blockChain.isValidBlockChain()) {
          pen.println("Chain is valid!");
        } else {
          pen.println("Chain is not valid!");
        }
        pen.println("");
      } else if (input.contentEquals("report")) {
        blockChain.printBalances();
        pen.println("");
      } else if (input.contentEquals("help")) {
        pen.println("Valid commands: ");
        pen.println("    mine: discovers the nonce for a given transaction");
        pen.println("    append: appends a new block onto the end of the chain");
        pen.println("    remove: removes the last block from the end of the chain");
        pen.println("    check: checks that the block chain is valid");
        pen.println("    report: reports the balances of Alice and Bob");
        pen.println("    help: prints this list of commands");
        pen.println("    quit: quits the program");
        pen.println("");
      } else {
        pen.println("Invalid command.");
        pen.println("");
      }
      
      // print blockChain and prepare for the next command
      
      pen.print(blockChain.toString());
      pen.println("Command?");
      input = scan.next();
    } // while
    scan.close();
  }
}
