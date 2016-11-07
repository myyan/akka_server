package com.maxtropy.iec104.frame;


/**
 * Created by chenhao on 2016/11/7.
 */
public class ResponseSFrame extends Iec104Frame {

    public static final byte CTRL_CODE = 0x01;

    public ResponseSFrame(byte sendSeq1, byte sendSeq2, byte respSeq1, byte respSeq2, byte[] data) {
        super(CTRL_CODE, sendSeq1, sendSeq2, respSeq1, respSeq2, data);
    }

    public int getRespSeqNumber() {
        int seqNum = 0;
        for (int i = 1; i < 8; i++) {
            seqNum += (respSeq1 >> i & 0x01) * (1 << (i - 1));
        }
        for (int j = 0; j < 8; j++) {
            seqNum += (respSeq2 >> j & 0x01) * (1 << (j + 7));
        }
        return seqNum;
    }
}
