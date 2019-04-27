package com.linxu.microapp.enums;

/**
 * @author linxu
 * @date 2019/4/27
 */
public enum Code {
    OK("200"),
    ERROR("200"),
    UNKONW("500");
    private String code;

    Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
