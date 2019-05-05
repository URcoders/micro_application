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
    ISEXIST("账户已经存在"),
    PROGRAM_SUCCESS("成功提交编程方案，正在给机器人下发指令，请注意机器人的行为！"),
    ROBOT_IS_NOT_EXIST("您还未绑定机器人，请先关联机器人，谢谢！"),
    GET_PROGRAMDATA_ERROR("编程方案获取失败，可能是网络或者机器原因"),
    GET_PROGRAMDATA_SUCCESS("编程方案获取成功"),
    WEB_DATA_ERROR("用户ID格式错误或者不存在！")
    ;
    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
