import com.maxtropy.iec104.actors.Server;
import akka.actor.ActorSystem;
import akka.io.Tcp;

/**
 * Created by chenhao on 2016/11/4.
 */
public class BootStrap {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("iec104_system");
        actorSystem.actorOf(Server.props(), "tcpManager");
    }
}
