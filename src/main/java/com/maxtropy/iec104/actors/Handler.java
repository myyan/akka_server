package com.maxtropy.iec104.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;

/**
 * Created by chenhao on 2016/11/4.
 */
public class Handler extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Tcp.Received) {
            final ByteString data = ((Tcp.Received) message).data();
            log.info("receive message from client {}, message {}", getSender(), data);
            getSender().tell(TcpMessage.write(data), getSelf());
            getSender().tell(TcpMessage.write(testData()), getSelf());

        } else if (message instanceof Tcp.ConnectionClosed) {
            log.info("receive close connection message from client :{}", getSender());
            getContext().stop(getSelf());
        } else {
            unhandled(message);
        }
    }

    private ByteString testData() {
        ByteStringBuilder sb = new ByteStringBuilder();
        sb.putByte((byte) 0x01);
        sb.putByte((byte) 0x02);
        sb.putByte((byte) 0x03);
        return sb.result();
    }

}
