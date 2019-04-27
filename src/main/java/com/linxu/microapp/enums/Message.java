package com.linxu.microapp.enums;

/**
 * @author linxu
 * @date 2019/4/27
 */
public enum Message {
    LOGIN_SUCCESS("登录成功"),
    LOGIN_FAIL("登录失败"),
    SEVER_ERROR("服务器错误"),
    REGISTER_SUCCESS("注册成功"),
    REGISTER_FAIL("注册失败"),
    OPERATION_SUCCESS("操作成功"),
    OPERATION_FAIL("操作失败"),
    ISEXIST("账户已经存在")
    ;
    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
