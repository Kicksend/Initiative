import org.java_websocket.WebSocket;

//public class MainActivity extends Activity 
public class MainActivity
{
    public static final int MAX_LEVEL = 15;

    public static WebSocket socket;
    public Map<Integer, byte[]> storage = 
        new HashMap<Integer, byte[]>();
    

    public static void processCommand(BitBuffer bits,
                                      int bitcount)
    {
        int userID = bits.readInt(13);
        int level = bits.readInt(5);
        MainActivity.socket.send(
            String.format("Processing %s\n", bits.dumpBits(bitcount)));

        if (level < 1 || level > MAX_LEVEL)
            return;

        Level lvl;
        try
        {
            Class c = Class.forName("Level" + level);
            lvl = (Level)c.newInstance();
        }
        catch (Exception ex)
        {
            return;
        }

        int score = lvl.run(userID, bits);
        String nickName = Scoreboard.getUser(userID);
        if (nickName == null)
        {
            MainActivity.socket.send("User not registered\n");
        }
        else
        {
            Scoreboard.scoreUser(userID, score, level);
            MainActivity.socket.send(String.format("<%s> Scoring %d on level %d.\n",
                nickName, score, level)); 
        }
    }
}
