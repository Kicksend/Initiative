import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class Level2 extends Level
{
    int run(int userID, BitBuffer buffer)
    {
        byte[] content = Level2.fetch(buffer.read7BitsChars());
        if (content == null)
            return 0;

        String strContent = new String(content).trim();
        if (strContent.equals("We are only warming up..." + userID))
            return (int)Math.pow(2, 2);

        return 0;
    }

    public static byte[] fetch(String googlSuffix)
    {
        String googlURL = "http://goo.gl/" + googlSuffix;
        if (!googlSuffix.matches("^[a-zA-Z0-9]+$"))
        {
            MainActivity.socket.send("Invalid suffix entered\n");
            return null;
        }

        try 
        {
            URL url = new URL(googlURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            url = new URL(conn.getHeaderField("Location"));
            conn = (HttpURLConnection)url.openConnection();
            try
            {
                InputStream is = new BufferedInputStream(conn.getInputStream());
                return IOUtils.toByteArray(is);
            }
            finally 
            {
                conn.disconnect();
            }
        }
        catch (IOException e) 
        {
            MainActivity.socket.send("Connection problems (" + googlURL + ")\n");
            return null;
        }
    }
}

