import akka.Client;
import akka.ClientHandler;
import akka.Server;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;

import java.net.InetSocketAddress;

/**
 * Created by chenhao on 2016/11/4.
 */
public class BootStrap {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("mySystem");
        actorSystem.actorOf(Server.props(Tcp.get(actorSystem).manager()),"tcpManager");
//        InetSocketAddress address = new InetSocketAddress("localhost",9999);
//        ActorRef clientHandler = actorSystem.actorOf(Props.create(ClientHandler.class),"clientHandler");
//        actorSystem.actorOf(Client.props(address,clientHandler),"client");

    }
}
