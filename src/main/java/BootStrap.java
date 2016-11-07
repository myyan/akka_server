import com.maxtropy.iec104.actors.Server;
import akka.actor.ActorSystem;
import akka.io.Tcp;

/**
 * Created by chenhao on 2016/11/4.
 */
public class BootStrap {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("mySystem");
        actorSystem.actorOf(Server.props(Tcp.get(actorSystem).manager()),"tcpManager");
//        InetSocketAddress address = new InetSocketAddress("localhost",2404);
//        ActorRef clientHandler = actorSystem.actorOf(Props.create(ClientHandler.class),"clientHandler");
//        actorSystem.actorOf(Client.props(address,clientHandler),"client");

    }
}
