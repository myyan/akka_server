package com.maxtropy.iec104.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;

import java.net.InetSocketAddress;

/**
 * Created by chenhao on 2016/11/4.
 */
public class Server extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);



    public static Props props() {
        return Props.create(Server.class);
    }

    @Override
    public void preStart() {
        final ActorRef tcp = Tcp.get(getContext().system()).manager();
        tcp.tell(TcpMessage.bind(getSelf(), new InetSocketAddress("127.0.0.1", 2404), 100), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Tcp.Bound) {
            log.info("bind ok");
        } else if (message instanceof Tcp.CommandFailed) {
            log.info("bind error");
            getContext().stop(getSelf());
        } else if (message instanceof Tcp.Connected) {
            log.info("some client connect to server ");
//            final ActorRef handler = getContext().actorOf(Props.create(Handler.class));
            final ActorRef handler = getContext().actorOf(Iec104Manager.props());
            getSender().tell(TcpMessage.register(handler), getSelf());
        }
    }


}
