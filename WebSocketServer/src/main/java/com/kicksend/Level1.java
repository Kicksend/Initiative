public class Level1 extends Level
{
    int run(int userID, BitBuffer buffer)
    {
        String nickname = buffer.read7BitsChars();

        if (!nickname.matches("^[a-zA-Z]+$"))
        {
            MainActivity.socket.send("Trying to create a user with an invalid nickname\n");
            return 0;
        }
        else
        {
            Scoreboard.createUser(nickname, userID);
            MainActivity.socket.send("<" + nickname + "> User created!\n");
            return (int)Math.pow(1, 2);
        }
    }
}
