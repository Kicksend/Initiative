import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Scoreboard
{
    private static final Scoreboard INSTANCE = new Scoreboard();

    public Map<Integer, String> nicknameMap = new HashMap<Integer, String>();
    public Map<Integer, Integer[]> scoreboard = new HashMap<Integer, Integer[]>();

    public static Scoreboard getInstance() 
    {
        return INSTANCE;
    }

    public static void scoreUser(int userID, int score, int level)
    {
        Scoreboard sb = Scoreboard.getInstance();
        Integer[] userScores = sb.scoreboard.get(userID);
        userScores[level] = score;
    }

    public void ranking()
    {
        Iterator it = this.scoreboard.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry)it.next();
            Integer[] scores = (Integer[])pairs.getValue();
            Integer userID = (Integer)pairs.getKey();
            String nickname = this.getUser((int)userID);
            int sum = 0;
            for (int i = 0; i < scores.length; i++)
            {
                if (scores[i] != null)
                    sum += (int)scores[i];
            }
            
            MainActivity.socket.send(nickname + ": " + sum);
            it.remove();
        }
    }

    public static String getUser(int userID)
    {
        Scoreboard sb = Scoreboard.getInstance();
        return sb.nicknameMap.get(userID);
    }

    public static void createUser(String userName, int userID)
    {
        Scoreboard sb = Scoreboard.getInstance();
        if (sb.nicknameMap.get(userID) == null)
        {
            sb.nicknameMap.put(userID, userName);
            sb.scoreboard.put(userID, new Integer[MainActivity.MAX_LEVEL]);
        }
    }
}
