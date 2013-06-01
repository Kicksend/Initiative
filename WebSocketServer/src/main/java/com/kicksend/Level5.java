import java.security.MessageDigest;
import java.util.Arrays;

public class Level5 extends Level
{
  int run(int userID, BitBuffer buffer)
  {
    byte[] content = Level2.fetch(buffer.read7BitsChars());
    byte[] lvl3_content = MainActivity.storage.get(userID);
    if (lvl3_content == null)
    {
      MainActivity.socket.send("Level 3?\n");
      return 0;
    }

    byte[] md5_1 = MD5(lvl3_content);
    byte[] md5_2 = MD5(content);

    // "I'm too lazy to write the parser. See Level2." - Spencer.
    String result = Level3.bf(content);
    String lvl3_result = Level3.bf(content);

    boolean same_md5 = Arrays.equals(md5_1, md5_2);
    if (same_md5 && !lvl3_result.equals(result))
      return (int)Math.pow(5, 2);

    else if (same_md5)
      MainActivity.socket.send("Close, but not enough\n");

    return 0;
  }

  static public byte[] MD5(byte[] content)
  {
    try
    {
      MessageDigest m = MessageDigest.getInstance("MD5");
      m.update(content);
      return m.digest();
    }
    catch (java.security.NoSuchAlgorithmException e) 
    {
      return null;
    }
  }
}
