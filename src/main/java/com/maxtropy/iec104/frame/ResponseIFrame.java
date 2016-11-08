package com.maxtropy.iec104.frame;


import java.util.Arrays;

/**
 * Created by chenhao on 2016/11/7.
 */
public class ResponseIFrame extends Iec104Frame {

    public static final byte CTRL_CODE = 0x00;

    public ResponseIFrame(byte sendSeq1, byte sendSeq2, byte respSeq1, byte respSeq2, byte[] data) {
        super(CTRL_CODE, sendSeq1, sendSeq2, respSeq1, respSeq2, data);
    }

    public int getSendSeqNumber() {
        int seqNum = 0;
        for (int i = 1; i < 8; i++) {
            seqNum += (sendSeq1 >> i & 0x01) * (1 << (i - 1));
        }
        for (int j = 0; j < 8; j++) {
            seqNum += (sendSeq2 >> j & 0x01) * (1 << (j + 7));
        }
        return seqNum;
    }

    public int getReceiverSeqNumber() {
        int seqNum = 0;
        for (int i = 1; i < 8; i++) {
            seqNum += (respSeq1 >> i & 0x01) * (1 << (i - 1));
        }
        for (int j = 0; j < 8; j++) {
            seqNum += (respSeq2 >> j & 0x01) * (1 << (j + 7));
        }
        return seqNum;
    }


    public byte[] getDataEntity() {
        return Arrays.copyOfRange(data, 4, data.length);
    }
}
