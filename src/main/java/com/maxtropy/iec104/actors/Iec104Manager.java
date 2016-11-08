package com.maxtropy.iec104.actors;

import akka.actor.AbstractLoggingFSM;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import com.maxtropy.iec104.frame.Iec104Frame;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import scala.concurrent.duration.FiniteDuration;

/**
 * Created by chenhao on 2016/11/8.
 */
public class Iec104Manager extends AbstractLoggingFSM<Iec104Manager.Status, Iec104Manager.Data> {


    public static Props props() {
        return Props.create(Iec104Manager.class);
    }

    private int receiverNumber=0;
    private int senderNumber=0;

    public Iec104Manager() {
        when(Status.CONNECTING, matchEvent(Object.class, this::handleFrame));
        when(Status.WORLING, matchEvent(String.class, this::handleString));
        onTransition(matchState(null, Status.CONNECTING, this::doTransition));
        startWith(Status.CONNECTING, null);
        initialize();
    }

    private void doTransition() {
        log().info("transition happen");
    }

    enum Status {
        CONNECTING,
        WORLING,
        STARTED,
        STOPPED,
        CLOSING
    }

    static class Data {

    }

    private State<Status, Data> handleString(String msg, Data data) {
        log().info("msg");
        if (msg.equals("start")) {
            return goTo(Status.STARTED).using(new Data());
        } else {
            return stay();
        }
    }

    private State<Status, Data> handleFrame(Object frame, Data data) {
        receiverNumber++;
        context().sender().tell(TcpMessage.write(testData()),self());
        log().info("receiver sequence number : {}",receiverNumber);
        return stay();
    }

    private State<Status,Data> handleOther(Object object,Data data){
        receiverNumber++;
        log().info("receiver sequence number : {}",receiverNumber);
        return stay();
    }

    private ByteString testData() {
        ByteStringBuilder sb = new ByteStringBuilder();
        sb.putByte((byte) 0x12);
        sb.putByte((byte) 0x22);
        sb.putByte((byte) 0x03);
        return sb.result();
    }


}
