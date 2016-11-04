package frame;

import lombok.Getter;

/**
 * Created by chenhao on 2016/11/4.
 */
public abstract class Iec104Frame {
    @Getter
    protected byte[] address;

    @Getter
    protected byte ctrlCode;

    @Getter
    protected byte[] data;
}
