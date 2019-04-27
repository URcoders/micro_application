package com.linxu.microapp.exceptions;

/**
 * @author linxu
 * @date 2019/4/26
 */
public class IllegalDataException extends Exception {
    public IllegalDataException(String message) {
        System.err.println(message);
    }

    public IllegalDataException() {
        super();
    }
}
