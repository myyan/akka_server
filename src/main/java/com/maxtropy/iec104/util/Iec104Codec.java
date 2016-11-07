package com.maxtropy.iec104.util;

import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import com.maxtropy.iec104.exception.Iec104DecodeControlCodeException;
import com.maxtropy.iec104.frame.Iec104Frame;
import com.maxtropy.iec104.frame.ResponseIFrame;
import com.maxtropy.iec104.frame.ResponseSFrame;
import com.maxtropy.iec104.frame.ResponseUFrame;
import lombok.val;

/**
 * Created by chenhao on 2016/11/4.
 */
public class Iec104Codec {

    private final static byte stx = 0x68;

    public ByteString encode(Iec104Frame frame) {
        val builder = new ByteStringBuilder();
        builder.putByte(stx);
        builder.putByte((byte) frame.getData().length);
        builder.putBytes(frame.getData());
        return builder.result();
    }

    public Iec104Frame decode(ByteString rawData) throws Iec104DecodeControlCodeException {
        val buf = rawData.asByteBuffer();
        buf.get();
        int dataLength = buf.get();
        Byte sendSeq1 = buf.get();
        Byte sendSeq2 = buf.get();
        Byte respSeq1 = buf.get();
        Byte respSeq2 = buf.get();
        byte[] data = new byte[dataLength];
        buf.get(data, 0, dataLength);
        byte ctrlCode = (byte) (sendSeq1 & 0x03);
        Iec104Frame frame;
        switch (ctrlCode) {
            case ResponseIFrame.CTRL_CODE:
                frame = new ResponseIFrame(sendSeq1, sendSeq2, respSeq1, respSeq2, data);
                break;
            case ResponseUFrame.CTRL_CODE:
                frame = new ResponseUFrame(sendSeq1, sendSeq2, respSeq1, respSeq2, data);
                break;
            case ResponseSFrame.CTRL_CODE:
                frame = new ResponseSFrame(sendSeq1, sendSeq2, respSeq1, respSeq2, data);
                break;
            default:
                throw new Iec104DecodeControlCodeException();
        }
        return frame;
    }

}
