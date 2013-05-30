import java.util.HashMap;
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
