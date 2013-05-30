import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.BitSet;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * Kicksend Initiative Web Server
 */
public class WebServer extends WebSocketServer 
{
    public WebServer(int port) throws UnknownHostException 
    {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) 
    {
        MainActivity.socket = conn;
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) 
    {
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " disconnected!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) 
    {
        BitSet bs = new BitSet(2048);
        BitBuffer bits = new BitBuffer(bs);
        bits.set(message);
        MainActivity.processCommand(bits, message.length());
    }

    public static void main(String[] args) throws InterruptedException , IOException 
    {
        WebSocketImpl.DEBUG = true;
        int port = 8887;
        try 
        {
            port = Integer.parseInt(args[0]);
        } 
        catch (Exception ex) {}

        WebServer s = new WebServer(port);
        s.start();

        System.out.println("Kicksend Initiative started on port: " + s.getPort());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) 
    {
        ex.printStackTrace();
    }
}
