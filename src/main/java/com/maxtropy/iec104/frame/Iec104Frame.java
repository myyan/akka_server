package com.maxtropy.iec104.frame;

import lombok.Getter;

/**
 * Created by chenhao on 2016/11/4.
 */
public abstract class Iec104Frame {

    @Getter
    protected byte ctrlCode;

    @Getter
    protected byte sendSeq1;

    @Getter
    protected byte sendSeq2;

    @Getter
    protected byte respSeq1;

    @Getter
    protected byte respSeq2;

    @Getter
    protected byte[] data;

    public Iec104Frame(byte ctrlCode, byte sendSeq1, byte sendSeq2, byte respSeq1, byte respSeq2, byte[] data) {
        this.ctrlCode = ctrlCode;
        this.sendSeq1 = sendSeq1;
        this.sendSeq2 = sendSeq2;
        this.respSeq1 = respSeq1;
        this.respSeq2 = respSeq2;
        this.data = data;
    }
}
