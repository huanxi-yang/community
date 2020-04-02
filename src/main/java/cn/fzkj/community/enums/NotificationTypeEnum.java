package cn.fzkj.community.enums;

import cn.fzkj.community.domain.Notification;

public enum NotificationTypeEnum {
    REPLAY_QUESTION(1,"回复了问题"),
    REPLAY_COMMENT(2,"回复了评论");

    private Integer type;
    private String name;

    NotificationTypeEnum(Integer status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String getTypeName(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
