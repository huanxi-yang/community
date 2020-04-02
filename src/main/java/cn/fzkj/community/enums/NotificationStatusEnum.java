package cn.fzkj.community.enums;

public enum NotificationStatusEnum {

    UNREAD(1),READ(0)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
