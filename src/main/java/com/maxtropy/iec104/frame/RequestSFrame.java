package com.maxtropy.iec104.frame;


/**
 * Created by chenhao on 2016/11/7.
 */
public class RequestSFrame extends Iec104Frame {

    public static final byte CTRL_CODE = 0x01;

    public RequestSFrame(byte ctrlCode, byte sendSeq1, byte sendSeq2, byte respSeq1, byte respSeq2, byte[] data) {
        super(CTRL_CODE, sendSeq1, sendSeq2, respSeq1, respSeq2, data);
    }
}
