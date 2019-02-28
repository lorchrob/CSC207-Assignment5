public class Hash {

  // Fields

  // An array of bytes to hold the hash data itself
  byte[] data;

  // Constructor

  // Purpose: Create a new Hash with a given array of bytes
  public Hash(byte[] data) {
    this.data = data;
  }

  // Methods

  // Purpose: Return the data field of the Hash
  public byte[] getData() {
    return this.data;
  }

  // Purpose: Check whether the given data represents a valid hash
  // Post: Return true if the first three bytes are 0s, false if not.
  public boolean isValid() {
    if (this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0) {
      return true;
    } else {
      return false;
    }
  }

  // Purpose: Find a String in hexadecimal form to represent the hash
  // Post: Return a String representation of the data field of the Hash
  public String toString() {
    String hex = "";
    for (int i = 0; i < data.length; i++) {
      hex += String.format("%02x", Byte.toUnsignedInt(data[i]));
    }
    return hex;
  }

  // Purpose: Check if one Hash is equal to another Hash
  // Post: Return true iff the data field of the parameter Hash has the same contents as the data
  //       field of the given Hash
  public boolean equals(Object other) {
    if (other instanceof Hash) {
      Hash o = (Hash) other;
      return o.data.equals(this.data);
    } else {
      return false;
    }
  }
}
