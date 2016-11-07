package com.maxtropy.iec104.exception;

/**
 * Created by chenhao on 2016/11/7.
 */
public class Iec104DecodeControlCodeException extends Iec104Exception {

    public Iec104DecodeControlCodeException() {
        super("the control code of the response com.maxtropy.iec104.akka.frame is invaliad");
    }
}
