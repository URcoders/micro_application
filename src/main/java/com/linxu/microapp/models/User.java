package com.linxu.microapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/4/26
 */
@Data
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.DEFAULT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private int id;
    private String userName;
    private String password;
    private String name;

    public static void main(String[] args) {
        System.out.println(new User());
    }
}
