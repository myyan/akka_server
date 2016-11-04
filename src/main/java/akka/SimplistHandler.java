package akka;

import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;

/**
 * Created by chenhao on 2016/11/4.
 */
public class SimplistHandler extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        //receive messgae
        if (message instanceof Tcp.Received) {
            final ByteString data = ((Tcp.Received) message).data();
            System.out.println("get data");
            System.out.println(data);
            getSender().tell(TcpMessage.write(data), getSelf());
            // get close message
        } else if (message instanceof Tcp.ConnectionClosed) {
            System.out.println("handler closed");
            getContext().stop(getSelf());
        }
    }
    public ByteString getString(){
        ByteStringBuilder stringBuilder = new ByteStringBuilder();
        stringBuilder.putBytes("123".getBytes());
        return stringBuilder.result();
    }
}
