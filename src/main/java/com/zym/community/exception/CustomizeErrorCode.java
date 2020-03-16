package com.zym.community.exception;

/**
 * @author
 * @date 2020/3/16-17:05
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找到的问题不存在了,换个问题试试?");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
