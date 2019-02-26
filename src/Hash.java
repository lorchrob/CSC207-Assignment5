import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class Hash {
  byte[] data;

  public Hash(byte[] data) {
    this.data = data;
  }

  public byte[] getData() {
    return this.data;
  }

  public boolean isValid() {
    if (this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0) {
      return true;
    } else {
      return false;
    }
  }

  public String toString() {
    String hex = "";
    for (int i = 0; i < data.length; i++) {
      hex += String.format("%02x", Byte.toUnsignedInt(data[i]));
    }
    return hex;
  }

  public boolean equals(Object other) {
    if (other instanceof Hash) {
      Hash o = (Hash) other;
      return o.data.equals(this.data);
    } else {
      return false;
    }
  }
}
