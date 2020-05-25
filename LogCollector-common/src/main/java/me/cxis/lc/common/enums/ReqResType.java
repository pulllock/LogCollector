package me.cxis.lc.common.enums;

public enum ReqResType {

    REQ    (0, "REQ"),
    RES    (1, "RES")
    ;

    ReqResType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ReqResType of(int type) {
        for (ReqResType value : ReqResType.values()) {
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
