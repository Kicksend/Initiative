import java.util.ArrayList;

public class Level3 extends Level
{
  private static final int MAX_CHARS = 300;

  int run(int userID, BitBuffer buffer)
  {
    byte[] content = Level2.fetch(buffer.read7BitsChars());

    String result = Level3.bf(content);
    if (result.equals("Initiative" + userID))
    {
      MainActivity.storage[userID] = content;
      return (int)Math.pow(3, 2);
    }

    return 0;
  }

  static public String bf(byte[] program)
  {
    String output = "";
    byte[] data = new byte[MAX_CHARS];
    int dataPointer = 0;
    ArrayList<Integer> charStack = new ArrayList<Integer>();
    for (int eip = 0; eip < program.length; eip++) 
    {
      switch (program[eip])
      {
        case '>':
          if (dataPointer + 1 <= data.length)
            dataPointer++;
          break;

        case '<':
          if (dataPointer - 1 >= 0)
            dataPointer--;
          break;

        case '+':
          if (data[dataPointer] + 1 <= Byte.MAX_VALUE)
            data[dataPointer]++;
          break;

        case '-':
          if (data[dataPointer] - 1 >= 0)
            data[dataPointer]--;
          break;

        case '.':
          output += (char)data[dataPointer];
          break;

        case ',':
          break;

        case '[':
          if (data[dataPointer] - 1 > 0)
            charStack.add(eip - 1);

          break;

        case ']':
          if (charStack.size() > 0) 
          {
            eip = charStack.get(charStack.size() - 1);
            charStack.remove(charStack.size() - 1);
          }
          break;
      }
    }

    return output.trim();
  }
}
