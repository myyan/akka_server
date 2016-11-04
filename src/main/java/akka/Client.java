package akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.japi.Procedure;
import akka.util.ByteString;

import java.net.InetSocketAddress;

/**
 * Created by chenhao on 2016/11/4.
 */
public class Client extends UntypedActor {

    final InetSocketAddress remote;

    final ActorRef listener;


    public static Props props(InetSocketAddress remote, ActorRef listener) {
        return Props.create(Client.class, remote, listener);
    }


    public Client(InetSocketAddress remote, ActorRef listener) {
        this.remote = remote;
        this.listener = listener;
        final ActorRef tcp = Tcp.get(context().system()).manager();
        tcp.tell(TcpMessage.connect(remote), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        //command failed means proxy failed to connect to server
        if (message instanceof Tcp.CommandFailed) {
            //option
            System.out.println("connected error");
            listener.tell("failed", getSelf());
            getContext().stop(getSelf());
        }
        //connected means  proxy has been connect to server
        else if (message instanceof Tcp.Connected) {
            System.out.println("connected ok");
            listener.tell(message, getSelf());
            // the connection has been established
            // so the next will register a handler
            // here the handler is self
            //and the state of self next will become a new role
            //the connection get message will send to this actor
            //handler is a actor  oppsite direction is a actor reference
            getSender().tell(TcpMessage.register(getSelf()), getSelf());
            //getsender is the proxy of server but we think it is server
            getContext().become(connected(getSender()));
        }
    }
    private Procedure<Object> connected(final ActorRef connection) {
        return new Procedure<Object>() {
            @Override
            public void apply(Object message) {
                if (message instanceof ByteString) {
                    //write bytestring to server
                    connection.tell(TcpMessage.write((ByteString) message), getSelf());
                } else if (message instanceof Tcp.CommandFailed) {

                } else if (message instanceof Tcp.Received) {
                    listener.tell(((Tcp.Received) message).data(), getSelf());
                } else if (message.equals("close")) {
                    connection.tell(TcpMessage.close(), getSelf());
                } else if (message instanceof Tcp.ConnectionClosed) {
                    getContext().stop(getSelf());
                }
            }
        };
    }

}
