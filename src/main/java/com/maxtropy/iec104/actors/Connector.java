package com.maxtropy.iec104.actors;

import akka.actor.AbstractLoggingFSM;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.maxtropy.iec104.util.Iec104Codec;

import java.net.InetSocketAddress;

/**
 * Created by chenhao on 2016/11/7.
 */
public class Connector extends AbstractLoggingFSM<Connector.ConnectorState, Connector.ConnectorData> {

    static Props props(InetSocketAddress address) {
        return Props.create(Connector.class, address);
    }

    private InetSocketAddress address;

    private ActorRef operator;

    private Iec104Codec codec = new Iec104Codec();

    private Connector(InetSocketAddress address) {
        this.address = address;
        startWith(ConnectorState.Connecting,new ConnectorData());
    }

    enum ConnectorState {
        Connecting,
        Idle;
    }

    static class ConnectorData {

    }
}
