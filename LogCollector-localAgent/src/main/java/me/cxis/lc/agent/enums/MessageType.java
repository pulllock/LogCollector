package me.cxis.lc.agent.enums;

public enum MessageType {

    CONNECT    (1, "连接"),
    HEART_BEAT (2, "心跳"),
    LOG        (3, "日志"),
    JVM        (4, "JVM数据")
    ;

    MessageType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static MessageType of(int type) {
        for (MessageType value : MessageType.values()) {
            if (type == value.type) {
                return value;
            }
        }
        return null;
    }

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
