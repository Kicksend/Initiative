import java.util.BitSet;

public class BitBuffer
{
    private BitSet bitSet;
    private int pointer;

    public BitBuffer(BitSet bitSet)
    {
        this.bitSet = bitSet;
    }

    public String dumpBits(int bitcount)
    {
        String buffer = new String();
        for (int i = 0; i < bitcount; i++)
            buffer += this.bitSet.get(i) ? "1": "0";

        return buffer;
    }

    public void set(String bits)
    {
        for (int i = 0; i < bits.length(); i++)
        {
            this.bitSet.set(i, bits.charAt(i) == '1');
        }
    }

    public String read7BitsChars() 
    {
        String temp = new String();
        for (int i = 0; i < this.bitSet.size() / 7; i++)
        {
            char chr = (char)this.readInt(7);
            if (chr == '\0')
                break;

            temp += chr;
        }

        return new String(temp);
    }

    public int readInt(int len)
    {
        int value = 0, j = 0;
        for (int i = this.pointer + len - 1; i >= this.pointer; i--)
        {
            value |= this.bitSet.get(i) ? (1 << j) : 0;
            j++;
        }

        this.pointer += len;
        return value;
    }
}
