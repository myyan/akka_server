package com.maxtropy.iec104.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;

import java.net.InetSocketAddress;

/**
 * Created by chenhao on 2016/11/4.
 */
public class Server extends UntypedActor {

    final ActorRef manager;

    public Server(ActorRef manager) {
        this.manager = manager;
    }

    public static Props props(ActorRef manager) {
        return Props.create(Server.class, manager);
    }

    @Override
    public void preStart() {
        final ActorRef tcp = Tcp.get(getContext().system()).manager();
        tcp.tell(TcpMessage.bind(getSelf(), new InetSocketAddress("127.0.0.1", 2404), 100), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        //this means bind ok

        //this is about server side own info when establish a connection  one sile server side
        if (message instanceof Tcp.Bound) {
            System.out.println("bind ok");
            manager.tell(message, getSelf());
        }
        //this means command failed
        // this is about server  one side server side
        else if (message instanceof Tcp.CommandFailed) {
            System.out.println("bind error");
            getContext().stop(getSelf());
        }
        //this means client sended message   just client proxy  but user don't care
        else if (message instanceof Tcp.Connected) {
            System.out.println("server connected");
            final Tcp.Connected con = (Tcp.Connected) message;
            manager.tell(con, getSelf());
            final ActorRef handler = getContext().actorOf(Props.create(Handler.class));
            getSender().tell(TcpMessage.register(handler), getSelf());
        }
    }
}
