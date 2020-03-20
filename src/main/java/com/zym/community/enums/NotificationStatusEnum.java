package com.zym.community.enums;

/**
 * @author
 * @date 2020/3/19-21:47
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
