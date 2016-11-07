package com.maxtropy.iec104.akka;

import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;

/**
 * Created by chenhao on 2016/11/4.
 */
public class Handler extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        //receive messgae
        if (message instanceof Tcp.Received) {
            final ByteString data = ((Tcp.Received) message).data();
            System.out.println("客户端发来信息:"+getSender());
            System.out.println(data);
            getSender().tell(TcpMessage.write(data), getSelf());
            getSender().tell(TcpMessage.write(testData()), getSelf());

        }
        // get close message
        else if (message instanceof Tcp.ConnectionClosed) {
            System.out.println("handler closed");
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
