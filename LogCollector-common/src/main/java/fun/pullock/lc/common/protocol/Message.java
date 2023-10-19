package fun.pullock.lc.common.protocol;

public class Message {

    private int type;

    private int reqType;

    private byte[] data;

    public Message(int type, int reqType, byte[] data) {
        this.type = type;
        this.reqType = reqType;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
