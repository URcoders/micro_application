package com.linxu.microapp.dtos;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/4/27
 */
@Data
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.DEFAULT)
public class ResponseData {
    private String message;
    private String code;
    private WrapData data;

    public static class Builder {
        private String message;
        private String code;
        private WrapData data;

        public Builder setMsg(String message) {
            this.message = message;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setData(WrapData data) {
            this.data = data;
            return this;
        }

        private void construct(ResponseData responseData) {
            responseData.setCode(code);
            responseData.setData(data);
            responseData.setMessage(message);
        }

        public ResponseData build() {
            ResponseData responseData = new ResponseData();
            construct(responseData);
            return responseData;
        }
    }

    //Test builder
    public static void main(String[] args) {
        ResponseData data = new ResponseData.Builder()
                .setCode("123")
                .setData(null)
                .setMsg("fsdd")
                .build();
        System.out.println(data);
    }
}
